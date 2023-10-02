package magic.potion.shop.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
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
        @JsonIgnoreProperties("potions")
        var recipe: Recipe,

        @ManyToOne
        @JoinColumn(name = "wizard_id")
        @JsonIgnoreProperties("potions")
        var wizard: Wizard? = null,


        @Column(name= "effect")
        @Enumerated(EnumType.STRING)
        var effect: PotionEffect,

        @Column(name= "power")
        var power: Long = 0,
) : RepresentationModel<Potion>()

