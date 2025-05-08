package org.example

import configureModules
import configureSerialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import routes.ApiRouting

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toIntOrNull() ?: 8080) {
        configureSerialization()
        configureModules()
        ApiRouting()
    }.start(wait = true)
}