package magic.potion.shop.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "recipe_ingredients")
data class RecipeIngredient(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    var recipeIngredient: Ingredient,

    @Column(name = "quantity")
    var quantity: BigDecimal
)
