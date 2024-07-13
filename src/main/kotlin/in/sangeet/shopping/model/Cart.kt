package `in`.sangeet.shopping.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*

@Entity(name = "carts")
@Schema(description = "The shopping cart of the user.")
data class Cart(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the cart.", example = "1", required = true)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "The ID of the user to whom the cart belongs.", example = "123", required = true)
    val user: User,

    @Schema(description = "Flag indicating whether the cart is checked out.", example = "false", required = false)
    var checkedOut: Boolean = false
)