package magic.potion.shop.model

import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name = "ingredients")
data class Ingredient(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(name = "name", nullable = false, length = 180, unique = true)
        var name: String = "",

        @Column(name = "flavor", nullable = false, length = 80)
        @Enumerated(EnumType.STRING)
        var flavor: IngredientFlavor = IngredientFlavor.FLAVORLESS
) : RepresentationModel<Ingredient>()
