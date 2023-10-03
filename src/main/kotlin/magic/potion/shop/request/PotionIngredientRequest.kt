package magic.potion.shop.request

import magic.potion.shop.model.IngredientFlavor
import java.math.BigDecimal

data class PotionIngredientRequest(

    var ingredientName: String,
    var flavor: IngredientFlavor,
    var quantity: BigDecimal,

    )
