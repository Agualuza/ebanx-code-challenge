package controllers

import converters.toModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import services.AccountService
import transport.EventDTO

class AccountController(
    private val accountService: AccountService
) {
    suspend fun processEvent(call: ApplicationCall) {
        val eventDTO = call.receive<EventDTO>()
        val event = eventDTO.toModel()

        accountService.processEvent(event)
        call.respond(
            HttpStatusCode.OK,
            event
        )
    }

    suspend fun getBalance(call: ApplicationCall) {
        val accountId = call.request.queryParameters["account_id"]?.toInt()
        val accountBalance = accountService.getBalance(accountId!!)
        call.respond(
            HttpStatusCode.OK,
            accountBalance
        )
    }

    suspend fun reset(call: ApplicationCall) {
        accountService.resetAccounts()
        call.respond(
            HttpStatusCode.OK,
            "OK"
        )
    }

}