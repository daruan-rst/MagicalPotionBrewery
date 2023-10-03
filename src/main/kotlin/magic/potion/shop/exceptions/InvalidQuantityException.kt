package magic.potion.shop.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidQuantityException : RuntimeException("The quantity for the ingredient is invalid!") {
}