package services

import io.ktor.server.plugins.*
import models.Account
import models.EvenType
import models.Event
import store.AccountStore

class AccountServiceImpl: AccountService {
    override fun processEvent(event: Event) =
        when (event.type) {
            EvenType.DEPOSIT -> handleDeposit(event)
            EvenType.WITHDRAW -> handleWithdraw(event)
            EvenType.TRANSFER -> handleTransfer(event)
        }

    override fun getBalance(id: Int) =
        AccountStore.getAccountById(id)?.balance ?: throw NotFoundException()

    private fun handleDeposit(event: Event) =
        AccountStore.getAccountById(event.destination!!)?.let { account ->
            createTransaction(event.destination!!, account.balance + event.amount )
        } ?: AccountStore.saveAccount(Account(event.destination, event.amount))

    private fun handleWithdraw(event: Event) =
        AccountStore.getAccountById(event.origin!!)?.let { account ->
            createTransaction(event.origin!!, account.balance - event.amount )
        } ?: throw NotFoundException()

    private fun handleTransfer(event: Event) =
        AccountStore.getAccountById(event.origin!!)?.let { originAccount ->
            AccountStore.getAccountById(event.destination!!)?.let { destinationAccount ->
                createTransaction(originAccount.id, originAccount.balance - event.amount)
                createTransaction(destinationAccount.id, destinationAccount.balance + event.amount)

            } ?: throw NotFoundException()
        } ?: throw NotFoundException()

    private fun createTransaction(id: Int, balance: Double) =
        Account(id, balance).let { updatedAccount ->
            AccountStore.deleteAccountById(id)
            AccountStore.saveAccount(updatedAccount)
        }
}