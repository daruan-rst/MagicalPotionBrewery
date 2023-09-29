package magic.potion.shop.model

import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name = "ingredients")
data class Ingredient(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(name = "name", nullable = false, length = 180)
        var name: String = "",

        @Enumerated(EnumType.STRING)
        @Column(name = "flavor", nullable = false)
        var flavor: IngredientFlavor = IngredientFlavor.FLAVORLESS

) : RepresentationModel<Ingredient>()
