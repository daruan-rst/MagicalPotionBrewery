package magic.potion.shop.repositories

import magic.potion.shop.model.Potion
import org.springframework.data.jpa.repository.JpaRepository

interface PotionRepository : JpaRepository<Potion, Long?> {
}