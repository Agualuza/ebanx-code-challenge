package services

import io.ktor.server.plugins.*
import models.Account
import models.EvenType
import models.Event
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import store.AccountStore
import kotlin.test.assertEquals

class AccountServiceImplTest {
    private val accountServiceImpl = AccountServiceImpl()

    @BeforeEach
    fun setup() {
        AccountStore.clearAccounts()
    }

    @Test
    fun `should reset accounts store`() {
        AccountStore.saveAccount(Account(1, 200.00))
        accountServiceImpl.resetAccounts()
        val account = AccountStore.getAccountById(1)
        assertEquals(null, account?.balance)
    }

    @Test
    fun `should return 404 to try get non-existing account balance`() {
        assertThrows(NotFoundException::class.java) {
            accountServiceImpl.getBalance(1)
        }
    }

    @Test
    fun `should create account with initial balance`() {
        val event = Event(EvenType.DEPOSIT, null, 1, 200.00)
        accountServiceImpl.processEvent(event)
        val account = AccountStore.getAccountById(1)
        assertEquals(200.00, account?.balance)
    }

    @Test
    fun `deposit into existing account`() {
        AccountStore.saveAccount(Account(1, 200.00))
        val event = Event(EvenType.DEPOSIT, null, 1, 200.00)
        accountServiceImpl.processEvent(event)
        val account = AccountStore.getAccountById(1)
        assertEquals(400.00, account?.balance)
    }

    @Test
    fun `Get balance for existing account`() {
        AccountStore.saveAccount(Account(1, 200.00))
        val balance = accountServiceImpl.getBalance(1)
        assertEquals(200.00, balance)
    }

    @Test
    fun `Withdraw from non-existing account`() {
        val event = Event(EvenType.WITHDRAW, 1, null, 200.00)
        assertThrows(NotFoundException::class.java) {
            accountServiceImpl.processEvent(event)
        }
    }

    @Test
    fun `Withdraw from existing account`() {
        AccountStore.saveAccount(Account(1, 200.00))
        val event = Event(EvenType.WITHDRAW, 1, null, 190.00)
        accountServiceImpl.processEvent(event)
        val account = AccountStore.getAccountById(1)
        assertEquals(10.00, account?.balance)
    }

    @Test
    fun `Transfer from existing account`() {
        AccountStore.saveAccount(Account(1, 200.00))
        val event = Event(EvenType.TRANSFER, 1, 2, 190.00)
        accountServiceImpl.processEvent(event)
        val account = AccountStore.getAccountById(1)
        val account2 = AccountStore.getAccountById(2)
        assertEquals(10.00, account?.balance)
        assertEquals(190.00, account2?.balance)
    }

    @Test
    fun `Transfer from non-existing account`() {
        val event = Event(EvenType.TRANSFER, 1, 2, 200.00)
        assertThrows(NotFoundException::class.java) {
            accountServiceImpl.processEvent(event)
        }
    }
}