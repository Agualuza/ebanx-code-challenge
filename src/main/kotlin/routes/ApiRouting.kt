package routes

import controllers.EventController
import io.ktor.http.*
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.koin.ktor.ext.inject

fun Application.ApiRouting() {
    routing {
        val eventController by inject<EventController>()

        post ("/event") {
            eventController.processEvent(call)
        }
        
    }
}