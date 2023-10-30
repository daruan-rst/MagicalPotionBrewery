package magic.potion.shop.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import jakarta.validation.constraints.Positive
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
    @JsonIgnoreProperties("potionIngredients")
    @JsonIgnore
    var wizard: Wizard,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    @JsonIgnoreProperties("potionIngredients")
    var wizardIngredient: Ingredient,

    @field:Positive(message = "A quantidade deve ser um número positivo")
    @Column(name = "quantity")
    var quantity: BigDecimal // in grams
) {
    constructor() : this(0, Wizard(), Ingredient(), BigDecimal.ZERO)
}


