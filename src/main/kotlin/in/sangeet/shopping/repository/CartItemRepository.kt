package `in`.sangeet.shopping.repository

import `in`.sangeet.shopping.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository

interface CartItemRepository : JpaRepository<CartItem, Long> {

    fun findByIdAndCartId(cartId: Long, productId: Long): CartItem?

    fun deleteByIdAndCartId(id: Long, cartId: Long)

    fun findByCartId(cartId: Long): List<CartItem>
}