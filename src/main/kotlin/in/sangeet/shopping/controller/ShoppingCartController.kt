package `in`.sangeet.shopping.controller

import `in`.sangeet.shopping.dto.UserRequest
import `in`.sangeet.shopping.model.Cart
import `in`.sangeet.shopping.model.CartItem
import `in`.sangeet.shopping.service.ShoppingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controller class for handling shopping cart related requests.
 */
@RestController
@RequestMapping("/cart")
class ShoppingCartController(private val shoppingService: ShoppingService) {

    /**
     * Creates a new cart for a user.
     *
     * @param userId ID of the user.
     * @return The created cart.
     */
    @Operation(summary = "Creates a new cart for user")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "New cart created",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = CartItem::class))]),
        ApiResponse(responseCode = "404", description = "User Not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
    ])
    @PostMapping
    fun createNewCart(@RequestParam userId: Long): ResponseEntity<Cart> {
        val cart = shoppingService.createNewCart(userId)
        return ResponseEntity.ok(cart)
    }

    /**
     * Adds an item to the cart.
     *
     * @param userRequest The request body containing all the details
     * @return Response message.
     */
    @Operation(summary = "Add an item to the cart")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Item added to cart",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = CartItem::class))]),
        ApiResponse(responseCode = "404", description = "Cart Not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
    ])
    @PostMapping("/items")
    fun addItemToCart(@RequestBody userRequest: UserRequest): ResponseEntity<CartItem> {
        val cart = shoppingService.addItemToCart(userRequest.userId, userRequest.productId, userRequest.quantity, userRequest.cartId)
        return ResponseEntity.ok(cart)
    }

    /**
     * Updates the quantity of an item in the cart.
     *
     * @param itemId ID of the cart item to update.
     * @param cartId ID of the cart to update.
     * @param quantity New quantity for the cart item.
     * @return Response message.
     */
    @Operation(summary = "Update the quantity of an item in the cart")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Item quantity updated",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = CartItem::class))]),
        ApiResponse(responseCode = "404", description = "Item not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
    ])
    @PutMapping("/{cartId}/items/{itemId}")
    fun updateItemQuantity(
        @PathVariable itemId: Long,
        @RequestParam quantity: Int,
        @PathVariable cartId: Long,
        @RequestParam userId: Long,
    ): ResponseEntity<CartItem> {
        val cartItem = shoppingService.updateItemQuantity(cartId, itemId, quantity, userId)
        return ResponseEntity.ok(cartItem)
    }

    /**
     * Removes an item from the cart.
     *
     * @param itemId ID of the cart item to remove.
     * @param cartId ID of the cart to update.
     * @return Response message.
     */
    @Operation(summary = "Remove an item from the cart.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Item removed from cart",
            content = [Content(mediaType = "application/json")]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
    ])
    @DeleteMapping("/{cartId}/items/{itemId}")
    fun removeItemFromCart(@PathVariable itemId: Long, @PathVariable cartId: Long, @RequestParam userId: Long,): ResponseEntity<String> {
        shoppingService.removeItemFromCart(userId, cartId, itemId)
        return ResponseEntity.ok("Item removed from cart")
    }

    /**
     * Retrieves all items in the user's active cart.
     *
     * @param userId ID of the user.
     * @param cartId ID of the cart to update.
     * @return List of cart items.
     */
    @Operation(summary = "Retrieve all items in the user's active cart")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Items retrieved",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = CartItem::class))]),
        ApiResponse(responseCode = "404", description = "Cart not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
    ])
    @GetMapping("/{cartId}")
    fun getCartItems(@RequestParam userId: Long, @PathVariable cartId: Long): ResponseEntity<List<CartItem>> {
        val items = shoppingService.getCartItems(cartId, userId)
        return ResponseEntity.ok(items)
    }

    /**
     * Retrieves the total price of the user's active cart.
     *
     * @param userId ID of the user.
     * @param cartId ID of the cart to update.
     * @return Total price of the cart.
     */
    @Operation(summary = "Retrieve the total price of the user's active cart")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Total price retrieved",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Double::class))]),
        ApiResponse(responseCode = "404", description = "Cart not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
    ])
    @GetMapping("/{cartId}/total")
    fun getCartTotal(@RequestParam userId: Long, @PathVariable cartId: Long): ResponseEntity<Double> {
        val total = shoppingService.getCartTotal(cartId, userId)
        return ResponseEntity.ok(total)
    }

    /**
     * Checks out the user's active cart.
     *
     * @param userId ID of the user.
     * @param cartId ID of the cart to update.
     * @return Response message.
     */
    @Operation(summary = "Check out the user's active cart")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Cart checked out successfully",
            content = [Content(mediaType = "application/json")]),
        ApiResponse(responseCode = "404", description = "Cart not found", content = [Content()]),
        ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
    ])
    @PostMapping("/{cartId}/checkout")
    fun checkoutCart(@RequestParam userId: Long, @PathVariable cartId: Long): ResponseEntity<String> {
        shoppingService.checkoutCart(cartId, userId)
        return ResponseEntity.ok("Cart checked out successfully")
    }
}