package magic.potion.shop.model

import jakarta.persistence.*

import org.springframework.hateoas.RepresentationModel

@Entity
data class Potion(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        @JoinColumn(name = "ingredient_id")
        var potionIngredient: Set<PotionIngredient>

) : RepresentationModel<Potion>()

