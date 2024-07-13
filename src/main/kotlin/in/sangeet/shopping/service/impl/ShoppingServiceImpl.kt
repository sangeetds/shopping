package `in`.sangeet.shopping.service.impl

import `in`.sangeet.shopping.exceptions.CartItemNotFoundException
import `in`.sangeet.shopping.exceptions.CartNotFoundException
import `in`.sangeet.shopping.exceptions.NoActiveCartFoundForTheUserException
import `in`.sangeet.shopping.model.Cart
import `in`.sangeet.shopping.model.CartItem
import `in`.sangeet.shopping.repository.CartRepository
import `in`.sangeet.shopping.service.CartItemService
import `in`.sangeet.shopping.service.ProductService
import `in`.sangeet.shopping.service.ShoppingService
import `in`.sangeet.shopping.service.UserService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for handling shopping cart operations.
 */
@Service
class ShoppingServiceImpl(
    private val cartRepository: CartRepository,
    private val cartItemService: CartItemService,
    private val productService: ProductService,
    private val userService: UserService,
) : ShoppingService {

    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun createNewCart(userId: Long): Cart {
        val user = userService.getUserById(userId)
        val cart = Cart(user = user)
        return cartRepository.save(cart)
    }

    @Transactional
    override fun addItemToCart(userId: Long, productId: Long, quantity: Int, cartId: Long): CartItem {
        val cart = getActiveCartForUser(cartId, userId)
        val product = productService.getProductById(productId)

        return try {
            val existingItem = cartItemService.findItemInCart(cartId, productId)
            existingItem.quantity += quantity
            logger.info { "Found item ${existingItem.product.name} in the cart" }
            cartItemService.save(existingItem)
        } catch (exc: CartItemNotFoundException) {
            logger.warn { "Item not found in the cart, saving a new one" }
            cartItemService.save(CartItem(cart = cart, product = product, quantity = quantity))
        }
    }

    @Transactional
    override fun updateItemQuantity(cartId: Long, cartItemId: Long, quantity: Int, userId: Long): CartItem {
        getActiveCartForUser(cartId, userId)

        val cartItem = cartItemService.findItemInCart(cartId, cartItemId)
        logger.info { "Found item ${cartItem.product.name} in the cart" }

        cartItem.quantity = quantity
        return cartItemService.save(cartItem)
    }

    @Transactional
    override fun removeItemFromCart(userId: Long, cartId: Long, cartItemId: Long) {
        getActiveCartForUser(cartId, userId)

        cartItemService.deleteItemInCart(cartId, cartItemId)
    }

    @Transactional(readOnly = true)
    override fun getCartItems(cartId: Long, userId: Long): List<CartItem> {
        val cart = cartRepository.findByIdAndUserIdAndCheckedOutFalse(cartId, userId) ?: throw CartNotFoundException()

        return cartItemService.findItemsInCart(cart.id!!)
    }

    @Transactional(readOnly = true)
    override fun getCartTotal(userId: Long, cartId: Long): Double {
        val cartItems = getCartItems(userId, cartId)
        return cartItems.sumOf { it.product.price * it.quantity }
    }

    @Transactional
    override fun checkoutCart(cartId: Long, userId: Long) {
        val cart = getActiveCartForUser(cartId, userId)
        cart.checkedOut = true
        cartRepository.save(cart)
    }

     /**
     * Retrieves the active (not checked out) cart for a given user.
     * If no active cart exists, a new one is created.
     *
     * @param userId The ID of the user whose active cart is to be retrieved.
     * @param cartId The ID of the active cart which is to be retrieved.
     * @return The active cart for the given user.
     */
    private fun getActiveCartForUser(cartId: Long, userId: Long): Cart {
        val cartForUser = cartRepository.findByIdAndUserIdAndCheckedOutFalse(cartId, userId)

        cartForUser ?: throw NoActiveCartFoundForTheUserException()

        return cartForUser
    }
}