package magic.potion.shop.repositories

import magic.potion.shop.model.PotionIngredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PotionIngredientRepository :  JpaRepository<PotionIngredient, Long>{
}