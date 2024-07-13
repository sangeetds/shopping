package `in`.sangeet.shopping.service

import `in`.sangeet.shopping.model.Cart
import `in`.sangeet.shopping.model.CartItem

interface ShoppingService {


    /**
     * Creates a new cart for a user.
     *
     * @param userId ID of the user.
     * @return The created cart.
     */
    fun createNewCart(userId: Long): Cart

    /**
     * Adds an item to the user's shopping cart. If the user does not have an active cart,
     * a new cart is created. If the item already exists in the cart, its quantity is updated.
     *
     * @param userId ID of the user.
     * @param productId ID of the product to add.
     * @param cartId ID of the product to add.
     * @param quantity Quantity of the product to add.
     */
    fun addItemToCart(userId: Long, productId: Long, quantity: Int, cartId: Long): CartItem

    /**
     * Updates the quantity of an item in the cart.
     *
     * @param cartId of the cart when the item is.
     * @param cartItemId ID of the cart item to update.
     * @param quantity New quantity for the cart item.
     *  * @param userId ID of the user.
     */
    fun updateItemQuantity(cartId: Long, cartItemId: Long, quantity: Int, userId: Long): CartItem

    /**
     * Removes an item from the cart.
     *
     * @param userId ID of the user.
     * @param cartItemId ID of the cart item to remove.
     * @param cartId ID of the cart from which item is to be removed.
     */
    fun removeItemFromCart(userId: Long, cartId: Long, cartItemId: Long)

    /**
     * Retrieves all items in the user's active cart.
     *
     * @param userId ID of the user.
     * @param cartId ID of the cart from which item are to be retrieved from.
     * @return List of cart items.
     */
    fun getCartItems(cartId: Long, userId: Long): List<CartItem>

    /**
     * Calculates the total price of all items in the user's active cart.
     *
     * @param userId ID of the user.
     * @param cartId ID of the cart from which item are to be retrieved from.
     * @return Total price of the cart.
     */
    fun getCartTotal(userId: Long, cartId: Long): Double

    /**
     * Checks out the user's active cart by setting its `checked_out` status to true.
     *
     * @param userId ID of the user.
     */
    fun checkoutCart(cartId: Long, userId: Long)

}