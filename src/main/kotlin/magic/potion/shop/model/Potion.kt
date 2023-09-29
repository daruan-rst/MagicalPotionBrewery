package magic.potion.shop.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name= "potions")
data class Potion(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        @get: NotBlank
        @Column(name = "potion_ingredient")
        @OneToMany(mappedBy = "potion", cascade = [CascadeType.ALL], orphanRemoval = true)
        var potionIngredient: Set<PotionIngredient> = emptySet(),

        @get: NotBlank
        @Column(name= "effect")
        var effect: PotionEffect,

        @get: NotBlank
        @Column(name= "power")
        var power: Long = 0

) : RepresentationModel<Potion>()

