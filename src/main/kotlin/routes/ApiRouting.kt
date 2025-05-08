package routes

import io.ktor.http.*
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import models.Event

fun Application.ApiRouting() {
    routing {
        post ("/") {
            val event = call.receive<Event>()
            call.respond(
                HttpStatusCode.OK,
                event
            )
        }
    }
}