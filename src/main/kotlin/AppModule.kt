import controllers.EventController
import org.koin.dsl.module
import services.EventService
import services.EventServiceImpl

val appModule = module {
    single<EventService> { EventServiceImpl() }
    single { EventController(get()) }
}