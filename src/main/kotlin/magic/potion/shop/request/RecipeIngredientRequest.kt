package magic.potion.shop.request

import magic.potion.shop.model.PotionIngredient

data class RecipeIngredientRequest(
    val name: String,
    val description: String,
    val potionIngredientList: List<PotionIngredient> = mutableListOf()
)
