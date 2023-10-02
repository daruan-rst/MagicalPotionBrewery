package magic.potion.shop.model

import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name = "wizards")
data class Wizard(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(name= "name", nullable = false, length = 180)
        var name: String = "",

        @OneToMany(mappedBy = "wizard", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var recipes: Set<Recipe> = emptySet(),

        @OneToMany(mappedBy = "wizard", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var potions: Set<Potion> = emptySet(),

        @OneToMany(mappedBy = "wizard", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var ingredientInventory: Set<PotionIngredient> = emptySet(),

        @OneToMany(mappedBy = "wizard", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var bottleInventory: Set<Bottle> = emptySet(),
) : RepresentationModel<Wizard>()
