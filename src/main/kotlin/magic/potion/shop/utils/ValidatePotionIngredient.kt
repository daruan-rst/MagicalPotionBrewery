package magic.potion.shop.utils

import magic.potion.shop.exceptions.InvalidQuantityException
import magic.potion.shop.request.PotionIngredientRequest
import java.math.BigDecimal

fun validatePotionIngredient(potionIngredient: PotionIngredientRequest){
    if (potionIngredient.quantity.compareTo(BigDecimal.ZERO) <= 0){
        throw InvalidQuantityException()
    }
}