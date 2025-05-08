package controllers

import converters.toModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import models.EvenType
import services.AccountService
import transport.AccountDTO
import transport.AccountResponse
import transport.EventDTO

class AccountController(
    private val accountService: AccountService
) {
    suspend fun processEvent(call: ApplicationCall) {
        val eventDTO = call.receive<EventDTO>()
        val event = eventDTO.toModel()

        try {
            accountService.processEvent(event)
            call.respond(
                HttpStatusCode.Created,
                getFormattedResponse(eventDTO)
            )
        } catch (e: NotFoundException) {
            call.respond(
                HttpStatusCode.NotFound,
                0
            )
        }
    }

    suspend fun getBalance(call: ApplicationCall) {
        try {
            val accountId = call.request.queryParameters["account_id"]?.toInt()
            val accountBalance = accountService.getBalance(accountId!!)
            call.respond(
                HttpStatusCode.OK,
                accountBalance
            )
        } catch (e: NotFoundException) {
            call.respond(
                HttpStatusCode.NotFound,
                0
            )
        }
    }

    suspend fun reset(call: ApplicationCall) {
        accountService.resetAccounts()
        call.respond(
            HttpStatusCode.OK,
            "OK"
        )
    }

    private fun getFormattedResponse(eventDTO: EventDTO) =
        when (eventDTO.type) {
            EvenType.DEPOSIT -> AccountResponse(destination = AccountDTO(eventDTO.destination!!.toString(), accountService.getBalance(eventDTO.destination!!)))
            EvenType.WITHDRAW -> AccountResponse(origin = AccountDTO(eventDTO.origin!!.toString(), accountService.getBalance(eventDTO.origin!!)))
            EvenType.TRANSFER -> AccountResponse(
                origin = AccountDTO(eventDTO.origin!!.toString(), accountService.getBalance(eventDTO.origin!!)),
                destination = AccountDTO(eventDTO.destination!!.toString(), accountService.getBalance(eventDTO.destination!!))
            )
        }


}