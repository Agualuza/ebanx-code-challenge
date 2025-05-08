package services

import models.Event

interface EventService {
    fun process(event: Event): Boolean
}