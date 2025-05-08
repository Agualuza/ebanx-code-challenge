package converters

import models.EvenType
import models.Event
import org.junit.jupiter.api.Test
import transport.EventDTO
import kotlin.test.assertEquals

class EventConverterTest {

    @Test
    fun `should convert EventDTO to Event`() {
        val event = Event(EvenType.TRANSFER, 1, 2, 100.00)
        val eventDTO = EventDTO(event.type, event.origin, event.destination, event.amount)

        val response = eventDTO.toModel()

        assertEquals(event, response)
    }
}