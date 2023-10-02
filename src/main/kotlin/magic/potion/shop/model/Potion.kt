package magic.potion.shop.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name= "potions")
data class Potion(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "recipe_id")
        var recipe: Recipe,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "wizard_id")
        var wizard: Wizard,

        @Column(name= "effect")
        @Enumerated(EnumType.STRING)
        var effect: PotionEffect,

        @Column(name= "power")
        var power: Long = 0,
) : RepresentationModel<Potion>()

