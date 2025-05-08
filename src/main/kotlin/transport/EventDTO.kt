package transport

import models.EvenType

class EventDTO(
    val type: EvenType,
    val origin: Int?,
    val destination: Int?,
    val amount: Double
)