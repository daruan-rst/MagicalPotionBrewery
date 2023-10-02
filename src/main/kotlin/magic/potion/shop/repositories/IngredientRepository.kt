package magic.potion.shop.repositories

import magic.potion.shop.model.Ingredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IngredientRepository : JpaRepository<Ingredient, Long?>{

    fun findIngredientByName(@Param("name") name: String) : Optional<Ingredient>
}