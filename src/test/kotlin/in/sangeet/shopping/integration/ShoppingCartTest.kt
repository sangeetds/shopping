package `in`.sangeet.shopping.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import com.fasterxml.jackson.databind.ObjectMapper
import `in`.sangeet.shopping.constants.TestConstants
import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_ID
import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_QUANTITY
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestCart
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestCartItem
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestProduct
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestUser
import `in`.sangeet.shopping.constants.TestConstants.Companion.getUserRequest
import `in`.sangeet.shopping.dto.UserRequest
import `in`.sangeet.shopping.model.Cart
import `in`.sangeet.shopping.model.CartItem
import `in`.sangeet.shopping.model.Product
import `in`.sangeet.shopping.repository.CartItemRepository
import `in`.sangeet.shopping.repository.CartRepository
import `in`.sangeet.shopping.repository.ProductRepository
import `in`.sangeet.shopping.repository.UserRepository
import org.junit.jupiter.api.*
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class ShoppingCartControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var cartRepository: CartRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var cartItemRepository: CartItemRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    private lateinit var userRequest: UserRequest
    private lateinit var product: Product
    private lateinit var cart: Cart
    private lateinit var cartItem: CartItem

    private val cartId = TEST_ID
    private val userId = TEST_ID
    private val productId = TEST_ID
    private val cartItemId = TEST_ID

    @BeforeAll
    fun setUp() {
        product = productRepository.save(getTestProduct(TEST_ID))
        userRepository.save(getTestUser(TEST_ID))
        cart = cartRepository.save(getTestCart(TEST_ID))
        cartItem = cartItemRepository.save(getTestCartItem(TEST_ID))
        userRequest = UserRequest(userId = userId, productId = productId, quantity = 1, cartId = cartId)
    }

    @Test
    @Transactional
    fun `addItemToCart should add item to cart`() {
        val newProduct = productRepository.save(getTestProduct(2))
        val newUserRequest = getUserRequest(newProduct.id!!)

        mockMvc.perform(post("/cart/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newUserRequest)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.quantity").value(TEST_QUANTITY))
    }

    @Test
    @Transactional
    fun `updateItemQuantity should update item quantity`() {
        mockMvc.perform(put("/cart/{cartId}/items/{itemId}", cartId, cartItemId)
            .param("userId", "1")
            .param("quantity", "2"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.quantity").value(2))
    }

    @Test
    @Transactional
    fun `removeItemFromCart should remove item from cart`() {
        mockMvc.perform(delete("/cart/{cartId}/items/{itemId}", cartId, cartItemId)
            .param("userId", "1"))
            .andExpect(status().isOk)
            .andExpect(content().string("Item removed from cart"))
    }

    @Test
    @Transactional
    fun `getCartItems should return all items in cart`() {
        mockMvc.perform(get("/cart/{cartId}", cartId)
            .param("userId", userId.toString()))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(cartItemId))
            .andExpect(jsonPath("$[0].quantity").value(5))
    }

    @Test
    @Transactional
    fun `getCartTotal should return total price of cart`() {
        mockMvc.perform(get("/cart/{cartId}/total", cartId)
            .param("userId", userId.toString()))
            .andExpect(status().isOk)
            .andExpect(content().string("500.0"))
    }

    @Test
    @Transactional
    fun `checkoutCart should checkout the cart`() {
        mockMvc.perform(post("/cart/{cartId}/checkout", cartId)
            .param("userId", userId.toString()))
            .andExpect(status().isOk)
            .andExpect(content().string("Cart checked out successfully"))
    }

    @Test
    @Transactional
    fun `addItemToCart should return bad request if product not found`() {
        val invalidUserRequest = UserRequest(userId = userId, productId = 999, quantity = 1, cartId = cartId)

        mockMvc.perform(post("/cart/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidUserRequest)))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    fun `updateItemQuantity should return bad request if cart item not found`() {
        mockMvc.perform(put("/cart/{cartId}/items/{itemId}", cartId, 999, userId)
            .param("quantity", "2")
            .param("userId", "1"))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    fun `getCartItems should return bad request if cart not found`() {
        mockMvc.perform(get("/cart/{cartId}", 999)
            .param("userId", userId.toString()))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    fun `getCartTotal should return bad request if cart not found`() {
        mockMvc.perform(get("/cart/{cartId}/total", 999)
            .param("userId", userId.toString()))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    fun `checkoutCart should return bad request if cart not found`() {
        mockMvc.perform(post("/cart/{cartId}/checkout", 999)
            .param("userId", userId.toString()))
            .andExpect(status().isNotFound)
    }
}