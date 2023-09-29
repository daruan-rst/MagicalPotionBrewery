package magic.potion.shop.model

import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel
import java.util.*

@Entity
@Table(name = "wizards")
data class Wizard(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(name= "name", nullable = false, length = 180)
        var name: String = "",

        @OneToMany
        @JoinColumn(name = "potion_id")
        var potionRecipes: Set<Potion> = emptySet(),

        @OneToMany
        var iventary: Set<PotionIngredient> = emptySet(),


) : RepresentationModel<Wizard>()
