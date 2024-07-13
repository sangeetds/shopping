package `in`.sangeet.shopping.repository

import `in`.sangeet.shopping.model.Cart
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long> {

    fun findByIdAndUserIdAndCheckedOutFalse(id: Long, userId: Long): Cart?
}

