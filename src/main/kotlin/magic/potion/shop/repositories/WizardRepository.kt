package magic.potion.shop.repositories

import magic.potion.shop.model.Wizard
import org.springframework.data.jpa.repository.JpaRepository

interface WizardRepository : JpaRepository<Wizard, Long?> {
}