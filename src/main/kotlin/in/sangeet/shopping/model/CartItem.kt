package `in`.sangeet.shopping.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*

@Entity
@Table(name = "cart_items")
@Schema(description = "Entity representing an item in the shopping cart.")
data class CartItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the cart item.", example = "1", required = true)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY) //  One to One mapping since one cartitem mapping to cart
    @JoinColumn(name = "cart_id")
    @Schema(description = "The cart to which this item belongs.", required = true)
    val cart: Cart,

    @ManyToOne(fetch = FetchType.LAZY) // One to One mapping since one product mapping to cartitem
    @JoinColumn(name = "product_id")
    @Schema(description = "The product added to the cart.", required = true)
    val product: Product,

    @Schema(description = "The quantity of the product in the cart.", example = "2", required = true)
    var quantity: Int
)