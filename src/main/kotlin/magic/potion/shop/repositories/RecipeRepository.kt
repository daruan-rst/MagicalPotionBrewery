package magic.potion.shop.repositories

import magic.potion.shop.model.Ingredient
import magic.potion.shop.model.Recipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RecipeRepository : JpaRepository<Recipe, Long?> {

    fun findByIngredientsIngredientName(ingredientName: String): List<Recipe>
}