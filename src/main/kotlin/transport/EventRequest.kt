package transport

import models.EvenType

class EventRequest(
    val type: EvenType,
    val origin: Int?,
    val destination: Int?,
    val amount: Double
)