package `in`.sangeet.shopping.service

import `in`.sangeet.shopping.exceptions.CartItemNotFoundException
import `in`.sangeet.shopping.model.CartItem

interface CartItemService {

    /**
     * Finds a cart item by product ID and cart ID.
     *
     * @param id The ID of the product.
     * @param cartId The ID of the cart.
     * @return The cart item if found.
     * @throws CartItemNotFoundException if the cart item is not found.
     */
    fun findItemInCart(id: Long, cartId: Long): CartItem

    /**
     * Saves a cart item.
     *
     * @param cartItem The cart item to save.
     * @return The saved cart item.
     */
    fun save(cartItem: CartItem): CartItem

    /**
     * Deletes a cart item by its ID and cart ID.
     *
     * @param cartId The ID of the cart.
     * @param id The ID of the cart item.
     */
    fun deleteItemInCart(cartId: Long, id: Long)

    /**
     * Finds all items in a cart by the cart ID.
     *
     * @param cartId The ID of the cart.
     * @return A list of cart items.
     */
    fun findItemsInCart(cartId: Long): List<CartItem>
}