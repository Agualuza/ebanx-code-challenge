package routes

import controllers.AccountController
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.routing
import io.ktor.server.routing.post
import io.ktor.server.routing.get
import org.koin.ktor.ext.inject

fun Application.ApiRouting() {
    routing {
        val accountController by inject<AccountController>()

        post ("/event") {
            accountController.processEvent(call)
        }

        get ("/balance") {
            accountController.getBalance(call)
        }

    }
}