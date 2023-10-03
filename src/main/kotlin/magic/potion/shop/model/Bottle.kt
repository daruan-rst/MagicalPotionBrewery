package magic.potion.shop.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name = "bottles")
data class Bottle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wizard_id", nullable = false)
    @JsonIgnoreProperties("wizard")
    @JsonIgnore
    var wizard: Wizard,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "potion_id")
    var potion: Potion? = null,

    @Column(name = "volume")
    @Min(0, message = "If the volume is 0, the bottle is empty")
    @Max(100, message = "If the volume is 100, the bottle is full")
    var volume: Long = 0
) : RepresentationModel<Bottle>() {

    constructor() : this(0, Wizard(), null, 0)
}



