package magic.potion.shop.request

data class RecipeIngredientRequest(
    val name: String,
    val description: String,
    val potionIngredientList: List<PotionIngredientRequest> = mutableListOf()
)
