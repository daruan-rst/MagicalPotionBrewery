package magic.potion.shop.repositories

import magic.potion.shop.model.RecipeIngredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipeIngredientRepository :  JpaRepository<RecipeIngredient, Long>{
}