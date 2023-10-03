package magic.potion.shop.request

import magic.potion.shop.model.IngredientFlavor
import java.math.BigDecimal

data class PotionIngredientRequest(
    val ingredientName: String,
    val flavor: IngredientFlavor,
    val quantity: BigDecimal
)
