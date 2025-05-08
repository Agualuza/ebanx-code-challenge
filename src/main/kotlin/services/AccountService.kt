package services

import models.Event

interface AccountService {
    fun processEvent(event: Event): Boolean

    fun getBalance(id: Int) : Double
}