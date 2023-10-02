package magic.potion.shop.repositories

import magic.potion.shop.model.Bottle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BottleRepository : JpaRepository<Bottle, Long?> {
}