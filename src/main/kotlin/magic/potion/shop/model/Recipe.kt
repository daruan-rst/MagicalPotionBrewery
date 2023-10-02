package magic.potion.shop.model

import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name = "recipes")
data class Recipe(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "name", nullable = false, length = 50)
    var name: String = "",

    @ManyToOne
    @JoinColumn(name = "wizard_id")
    var wizard: Wizard? = null,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "recipe_ingredients_recipes",
        joinColumns = [JoinColumn(name = "recipe_id")],
        inverseJoinColumns = [JoinColumn(name = "recipe_ingredient_id")]
    )
    var ingredients: Set<RecipeIngredient> = emptySet(),

    @Column(name = "description", nullable = false, length = 180)
    var description: String = ""
) : RepresentationModel<Recipe>()
