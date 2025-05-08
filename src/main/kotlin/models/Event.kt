package models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

data class Event(
    val type: EvenType,
    val origin: Int?,
    val destination: Int?,
    val amount: Double
)

enum class EvenType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER;

    companion object {
        @JsonCreator
        @JvmStatic
        fun from(value: String): EvenType = valueOf(value.uppercase())
    }

    @JsonValue
    fun toValue(): String = this.name.lowercase()
}
