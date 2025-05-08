import controllers.AccountController
import org.koin.dsl.module
import services.AccountService
import services.AccountServiceImpl

val appModule = module {
    single<AccountService> { AccountServiceImpl() }
    single { AccountController(get()) }
}