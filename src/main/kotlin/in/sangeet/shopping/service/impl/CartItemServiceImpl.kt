package `in`.sangeet.shopping.service.impl

import `in`.sangeet.shopping.exceptions.CartItemNotFoundException
import `in`.sangeet.shopping.model.CartItem
import `in`.sangeet.shopping.repository.CartItemRepository
import `in`.sangeet.shopping.service.CartItemService
import org.springframework.stereotype.Service

/**
 * Service class for handling operations related to shopping carts.
 *
 * @property cartItemRepository Repository for accessing cart data.
 */
@Service
class CartItemServiceImpl(private val cartItemRepository: CartItemRepository): CartItemService {


    override fun findItemInCart(id: Long, cartId: Long): CartItem =
        cartItemRepository.findByIdAndCartId(id, cartId) ?: throw CartItemNotFoundException("Item not found in the cart")

    override fun save(cartItem: CartItem) = cartItemRepository.save(cartItem)

    override fun deleteItemInCart(cartId: Long, id: Long) = cartItemRepository.deleteByIdAndCartId(cartId, id)

    override fun findItemsInCart(cartId: Long): List<CartItem> = cartItemRepository.findByCartId(cartId)

}