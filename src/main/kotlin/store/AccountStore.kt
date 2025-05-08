package store

import models.Account
import java.util.concurrent.CopyOnWriteArrayList

object AccountStore {
    val accounts = CopyOnWriteArrayList<Account>()

    fun getAccountById(id: Int) =
        this.accounts.firstOrNull { it.id == id }

    fun deleteAccountById(id: Int) =
        this.accounts.removeIf { it.id == id }

    fun saveAccount(account: Account) =
        this.accounts.add(account)

    fun clearAccounts() =
        this.accounts.clear()
}