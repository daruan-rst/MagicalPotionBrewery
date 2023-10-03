package magic.potion.shop.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel
import java.math.BigDecimal

@Entity
@Table(name = "potion_ingredient")
data class PotionIngredient(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wizard_id")
    @JsonIgnoreProperties("potion_ingredient")
    var wizard: Wizard,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    @JsonIgnoreProperties("potion_ingredient")
    var wizardIngredient: Ingredient,

    @Column(name = "quantity")
    var quantity: BigDecimal // in grams


) {
    constructor() : this(0, Wizard(), Ingredient(), BigDecimal.ZERO)

}

