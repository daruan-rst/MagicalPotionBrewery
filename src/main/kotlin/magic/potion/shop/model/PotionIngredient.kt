package magic.potion.shop.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal

@Entity
data class PotionIngredient(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        @ManyToOne
        @JoinColumn(name = "potion_id")
        var potion: Potion,

        @ManyToOne
        @JoinColumn(name = "ingredient_id")
        var ingredient: Ingredient,

        var quantity: BigDecimal // in grams


)
