import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import routes.ApiRouting

fun main() {
    embeddedServer(Netty, port = 8080) {
        configureSerialization()
        ApiRouting()
    }.start(wait = true)
}