package transport

import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
class AccountResponse(
    val origin: AccountDTO? = null,
    val destination: AccountDTO? = null
)