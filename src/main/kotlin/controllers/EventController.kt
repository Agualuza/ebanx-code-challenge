package controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import transport.EventDTO

class EventController {
    suspend fun processEvent(call: ApplicationCall) {
        val event = call.receive<EventDTO>()
        call.respond(
            HttpStatusCode.OK,
            event
        )
    }

}