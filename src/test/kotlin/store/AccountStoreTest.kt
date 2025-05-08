package store

import models.Account
import kotlin.test.*

class AccountStoreTest {

    @BeforeTest
    fun setup() {
        AccountStore.clearAccounts()
    }

    @Test
    fun `should save and return account when exist`() {
        val account = Account(100, 100.00)
        AccountStore.saveAccount(account)
        val response = AccountStore.getAccountById(100)

        assertNotNull(response)
        assertEquals(account, response)
    }

    @Test
    fun `should return null when not exist`() {
        val response = AccountStore.getAccountById(100)

        assertNull(response)
    }

    @Test
    fun `should save and delete account`() {
        val account = Account(100, 100.00)
        AccountStore.saveAccount(account)
        AccountStore.deleteAccountById(100)
        val response = AccountStore.getAccountById(100)

        assertNull(response)
    }

    @Test
    fun `should save and clear accounts`() {
        val account = Account(100, 100.00)
        AccountStore.saveAccount(account)
        AccountStore.clearAccounts()
        val response = AccountStore.getAccountById(100)

        assertNull(response)
    }
}