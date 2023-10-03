package magic.potion.shop.model

import com.fasterxml.jackson.annotation.JsonIgnore
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

        @OneToMany(mappedBy = "wizard", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        var recipes: Set<Recipe> = mutableSetOf(),

        @OneToMany(mappedBy = "wizard", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        var ingredientInventory: Set<PotionIngredient> = mutableSetOf(),

        @OneToMany(mappedBy = "wizard", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        var bottleInventory: Set<Bottle> = mutableSetOf(),
) : RepresentationModel<Wizard>(){
        override fun hashCode(): Int {
                return Objects.hash(id, name)
        }

}
