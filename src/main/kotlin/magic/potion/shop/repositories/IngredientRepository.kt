package magic.potion.shop.repositories

import magic.potion.shop.model.Ingredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IngredientRepository : JpaRepository<Ingredient, Long?>{
}