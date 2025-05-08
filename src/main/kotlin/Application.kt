import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson
import com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING


fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            configure(READ_ENUMS_USING_TO_STRING, true)
        }
    }
}