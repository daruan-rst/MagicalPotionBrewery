package magic.potion.shop.repositories

import magic.potion.shop.model.Recipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository : JpaRepository<Recipe, Long?> {

    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.ingredients ri WHERE ri.recipeIngredient.name = :ingredientName")
    fun findByIngredientsIngredientName(@Param("ingredientName") ingredientName: String): List<Recipe>
}