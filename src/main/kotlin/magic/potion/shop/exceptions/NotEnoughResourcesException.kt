package magic.potion.shop.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class NotEnoughResourcesException(message: String) : RuntimeException("Not enough resources for $message") {
}