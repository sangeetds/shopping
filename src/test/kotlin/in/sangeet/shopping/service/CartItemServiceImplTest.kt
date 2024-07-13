package `in`.sangeet.shopping.service

import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_ID
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestCartItem
import `in`.sangeet.shopping.exceptions.CartItemNotFoundException
import `in`.sangeet.shopping.repository.CartItemRepository
import `in`.sangeet.shopping.service.impl.CartItemServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CartItemServiceImplTest {

    private val cartItemRepository: CartItemRepository = mock()
    private lateinit var cartItemService: CartItemService

    @BeforeEach
    fun setup() {
        cartItemService = CartItemServiceImpl(cartItemRepository)
    }
    
    @Test
    fun `findItemInCart returns cart item if found`() {
        whenever(cartItemRepository.findByCartIdAndProductId(TEST_ID, TEST_ID)).thenReturn(getTestCartItem(TEST_ID))

        val result = cartItemService.findItemInCart(TEST_ID, TEST_ID)

        assertEquals(getTestCartItem(TEST_ID), result)
    }

    @Test
    fun `save persists the cart item`() {
        whenever(cartItemRepository.save(getTestCartItem(TEST_ID))).thenReturn(getTestCartItem(TEST_ID))

        val result = cartItemService.save(getTestCartItem(TEST_ID))

        assertEquals(getTestCartItem(TEST_ID), result)
        verify(cartItemRepository).save(getTestCartItem(TEST_ID))
    }

    @Test
    fun `deleteItemInCart deletes the cart item`() {
        cartItemService.deleteItemInCart(TEST_ID, TEST_ID)

        verify(cartItemRepository).deleteByIdAndCartId(TEST_ID, TEST_ID)
    }

    @Test
    fun `findItemsInCart returns list of cart items`() {
        val cartItems = listOf(getTestCartItem(TEST_ID))
        whenever(cartItemRepository.findByCartId(TEST_ID)).thenReturn(cartItems)

        val result = cartItemService.findItemsInCart(TEST_ID)

        assertEquals(cartItems, result)
    }

    // Negative Tests

    @Test
    fun `findItemInCart throws CartItemNotFoundException if item not found`() {
        whenever(cartItemRepository.findByCartIdAndProductId(TEST_ID, TEST_ID)).thenReturn(null)

        assertThrows(CartItemNotFoundException::class.java) {
            cartItemService.findItemInCart(TEST_ID, TEST_ID)
        }
    }

    @Test
    fun `deleteItemInCart does not throw any exception when item not found`() {
        doNothing().whenever(cartItemRepository).deleteByIdAndCartId(TEST_ID, TEST_ID)

        assertDoesNotThrow {
            cartItemService.deleteItemInCart(TEST_ID, TEST_ID)
        }

        verify(cartItemRepository).deleteByIdAndCartId(TEST_ID, TEST_ID)
    }

    @Test
    fun `findItemsInCart returns empty list if no items found`() {
        whenever(cartItemRepository.findByCartId(TEST_ID)).thenReturn(emptyList())

        val result = cartItemService.findItemsInCart(TEST_ID)

        assertTrue(result.isEmpty())
    }
}