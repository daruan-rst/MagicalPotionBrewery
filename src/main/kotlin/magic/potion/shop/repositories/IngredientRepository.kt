package magic.potion.shop.repositories

import magic.potion.shop.model.Ingredient
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository : JpaRepository<Ingredient, Long?>{
}