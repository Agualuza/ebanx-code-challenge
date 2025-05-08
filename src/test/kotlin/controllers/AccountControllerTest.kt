package controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import configureSerialization
import converters.toModel
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import models.EvenType
import org.junit.jupiter.api.AfterEach
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import routes.ApiRouting
import kotlin.test.Test
import services.AccountService
import transport.AccountDTO
import transport.AccountResponse
import transport.EventDTO
import kotlin.test.assertEquals

class AccountControllerTest {
    private val accountService: AccountService = mockk()

    @AfterEach
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun `should reset accounts and return OK`() = testApplication {
        every { accountService.resetAccounts() } returns Unit

        application {
            this@application.install(Koin) {
                modules(
                    module {
                        single<AccountService>() { accountService }
                        single { AccountController(get()) }
                    }
                )
            }
            configureSerialization()
            ApiRouting()
        }

        val response = client.post("/reset")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("OK", response.bodyAsText())

        verify(exactly = 1) { accountService.resetAccounts() }
    }

    @Test
    fun `should get account balance and return OK`() = testApplication {
        every { accountService.getBalance(1) } returns 20.0

        application {
            this@application.install(Koin) {
                modules(
                    module {
                        single<AccountService>() { accountService }
                        single { AccountController(get()) }
                    }
                )
            }
            configureSerialization()
            ApiRouting()
        }

        val response = client.get("/balance?account_id=1")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("20.0", response.bodyAsText())

        verify(exactly = 1) { accountService.getBalance(any()) }
    }

    @Test
    fun `should get non-existing account balance and return 404`() = testApplication {
        every { accountService.getBalance(1) } throws NotFoundException("")

        application {
            this@application.install(Koin) {
                modules(
                    module {
                        single<AccountService>() { accountService }
                        single { AccountController(get()) }
                    }
                )
            }
            configureSerialization()
            ApiRouting()
        }

        val response = client.get("/balance?account_id=1")

        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("0", response.bodyAsText())

        verify(exactly = 1) { accountService.getBalance(any()) }
    }

    @Test
    fun `should return 404 when processEvent returns 404`() = testApplication {
        val eventDTO = EventDTO(EvenType.WITHDRAW, 1, null, 20.0)
        every { accountService.processEvent(eventDTO.toModel()) } throws NotFoundException("")

        application {
            this@application.install(Koin) {
                modules(
                    module {
                        single<AccountService>() { accountService }
                        single { AccountController(get()) }
                    }
                )
            }
            configureSerialization()
            ApiRouting()
        }

        val response = client.post("/event") {
            contentType(ContentType.Application.Json)
            setBody(jacksonObjectMapper().writeValueAsString(eventDTO))
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("0", response.bodyAsText())

        verify(exactly = 1) { accountService.processEvent(any()) }
    }

    @Test
    fun `should return 201 when processEvent with success`() = testApplication {
        val eventDTO = EventDTO(EvenType.WITHDRAW, 1, null, 20.0)
        val expected = AccountResponse(origin = AccountDTO("1", 20.00))
        every { accountService.processEvent(eventDTO.toModel()) } returns true
        every { accountService.getBalance(1) } returns 20.00

        application {
            this@application.install(Koin) {
                modules(
                    module {
                        single<AccountService>() { accountService }
                        single { AccountController(get()) }
                    }
                )
            }
            configureSerialization()
            ApiRouting()
        }

        val response = client.post("/event") {
            contentType(ContentType.Application.Json)
            setBody(jacksonObjectMapper().writeValueAsString(eventDTO))
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals(jacksonObjectMapper().writeValueAsString(expected), response.bodyAsText())

        verify(exactly = 1) { accountService.processEvent(any()) }
        verify(exactly = 1) { accountService.getBalance(any()) }
    }
}