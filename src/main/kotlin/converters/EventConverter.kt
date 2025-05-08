package converters

import models.Event
import transport.EventDTO

fun EventDTO.toModel() =
    Event(
        type = this.type,
        origin = this.origin,
        destination = this.destination,
        amount = this.amount
    )