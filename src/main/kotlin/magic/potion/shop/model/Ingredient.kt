package magic.potion.shop.model

import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name = "ingredients")
data class Ingredient(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        @Column(name = "name", nullable = false, length = 180)
        var ingredientName: String = ""

) : RepresentationModel<Ingredient>()
