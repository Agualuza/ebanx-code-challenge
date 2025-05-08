package transport

import models.EvenType

data class EventDTO(
    val type: EvenType,
    val origin: Int?,
    val destination: Int?,
    val amount: Double
)