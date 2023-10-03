package magic.potion.shop.repositories

import magic.potion.shop.model.Ingredient
import magic.potion.shop.model.RecipeIngredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

@Repository
interface RecipeIngredientRepository :  JpaRepository<RecipeIngredient, Long>{

    @Query("SELECT ri \n" +
            "FROM RecipeIngredient ri \n" +
            "JOIN ri.recipeIngredient ingredient \n" +
            "WHERE ingredient.name = :ingredientName \n" +
            "AND ri.quantity = :quantity\n")
    fun findRecipeIngredientByIngredientAndQuantity(ingredient: Ingredient, quantity: BigDecimal): Optional<RecipeIngredient>
}