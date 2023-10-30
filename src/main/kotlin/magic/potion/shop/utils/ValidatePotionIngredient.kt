package magic.potion.shop.utils

import magic.potion.shop.exceptions.InvalidQuantityException
import magic.potion.shop.model.PotionIngredient
import java.math.BigDecimal

fun validatePotionIngredient(potionIngredient: PotionIngredient){
    if (potionIngredient.quantity.compareTo(BigDecimal.ZERO) <= 0){
        throw InvalidQuantityException()
    }
}