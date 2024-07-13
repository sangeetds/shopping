package `in`.sangeet.shopping.constants

import `in`.sangeet.shopping.dto.UserRequest
import `in`.sangeet.shopping.model.Cart
import `in`.sangeet.shopping.model.CartItem
import `in`.sangeet.shopping.model.Product
import `in`.sangeet.shopping.model.User

class TestConstants {

    companion object {

        const val TEST_ID           = 1L
        const val TEST_QUANTITY     = 5
        const val TEST_NAME         = "Name"
        const val TEST_DESCRIPTION  = "Description"
        const val TEST_EMAIL        = "sangeet@gmail.com,"
        const val TEST_PASSWORD     = "veryeasypassword"
        const val TEST_PRICE        = 100.0

        fun getTestCart(id: Long) = Cart(id, getTestUser(TEST_ID))

        fun getTestCartItem(id: Long) = CartItem(
            id = id,
            cart = getTestCart(TEST_ID),
            product = getTestProduct(TEST_ID),
            quantity = TEST_QUANTITY
        )

        fun getTestProduct(id: Long) = Product(
            id,
            TEST_NAME,
            TEST_DESCRIPTION,
            TEST_PRICE
        )

        fun getTestUser(id: Long) = User(
            id,
            TEST_NAME,
            TEST_EMAIL,
            TEST_PASSWORD
        )

        fun getUserRequest(productId: Long) = UserRequest(
            TEST_ID,
            TEST_ID,
            productId,
            TEST_QUANTITY,
        )
    }
}