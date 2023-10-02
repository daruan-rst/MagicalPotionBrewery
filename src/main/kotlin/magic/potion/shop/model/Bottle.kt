package magic.potion.shop.model

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name = "bottles")
data class Bottle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wizard_id", nullable = false)
    var wizard: Wizard,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "potion_id")
    var potion: Potion? = null,

    @get: NotBlank
    @Column(name = "volume")
    @Min(0, message = "If the volume is 0, the bottle is empty")
    @Max(100, message = "If the volume is 100, the bottle is full")
    var volume: Long = 0
) : RepresentationModel<Bottle>()



