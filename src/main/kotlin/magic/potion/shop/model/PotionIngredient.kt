package magic.potion.shop.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "ingredient_inventory")
data class PotionIngredient(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wizard_id", nullable = false)
    var wizard: Wizard,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    var wizardIngredient: Ingredient,

    @Column(name = "quantity")
    var quantity: BigDecimal // in grams


)

