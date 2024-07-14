package `in`.sangeet.shopping.service

import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_ID
import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_PRICE
import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_QUANTITY
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestCart
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestCartItem
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestProduct
import `in`.sangeet.shopping.exceptions.CartItemNotFoundException
import `in`.sangeet.shopping.exceptions.NoActiveCartFoundForTheUserException
import `in`.sangeet.shopping.repository.CartRepository
import `in`.sangeet.shopping.service.impl.ShoppingServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ShoppingServiceImplTest {

    private val cartRepository: CartRepository = mock()

    private val cartItemService: CartItemService = mock()

    private val productService: ProductService= mock()

    private val userService: UserService = mock()

    private lateinit var shoppingService: ShoppingService

    @BeforeEach
    fun setUp() {
        shoppingService = ShoppingServiceImpl(cartRepository, cartItemService, productService, userService)
    }

    @Test
    fun `addItemToCart adds a new item to the cart`() {
        whenever(cartRepository.findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)).thenReturn(getTestCart(TEST_ID) )
        whenever(productService.getProductById(TEST_ID)).thenReturn(getTestProduct(TEST_ID) )
        whenever(cartItemService.findItemInCart(TEST_ID, TEST_ID)).doAnswer { throw CartItemNotFoundException("Message") }

        shoppingService.addItemToCart(TEST_ID, TEST_ID, TEST_QUANTITY, TEST_ID)

        verify(cartItemService).save(any())
        verify(cartRepository, times(1)).findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)
        verify(productService, times(1)).getProductById(TEST_ID)
        verify(cartItemService, times(1)).findItemInCart(TEST_ID, TEST_ID)
    }

    @Test
    fun `addItemToCart updates quantity of existing item`() {
        val testCartItem = getTestCartItem(TEST_ID)

        whenever(cartRepository.findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)).thenReturn(getTestCart(TEST_ID) )
        whenever(productService.getProductById(TEST_ID)).thenReturn(getTestProduct(TEST_ID) )
        whenever(cartItemService.findItemInCart(TEST_ID, TEST_ID)).thenReturn(testCartItem )

        shoppingService.addItemToCart(TEST_ID, TEST_ID, TEST_QUANTITY, TEST_ID)

        assertEquals(10, testCartItem.quantity)

        verify(cartItemService).save(testCartItem)
        verify(cartRepository, times(1)).findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)
        verify(productService, times(1)).getProductById(TEST_ID)
        verify(cartItemService, times(1)).findItemInCart(TEST_ID, TEST_ID)
    }

    @Test
    fun `updateItemQuantity updates the quantity of an item`() {
        val testCartItem = getTestCartItem(TEST_ID)
        whenever(cartItemService.findItemInCart(TEST_ID, TEST_ID)).thenReturn(testCartItem )
        whenever(cartRepository.findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)).thenReturn(getTestCart(TEST_ID) )

        shoppingService.updateItemQuantity(TEST_ID, TEST_ID, TEST_QUANTITY, TEST_ID)

        assertEquals(TEST_QUANTITY, testCartItem.quantity)

        verify(cartItemService).save(testCartItem)
        verify(cartItemService, times(1)).findItemInCart(TEST_ID, TEST_ID)
    }

    @Test
    fun `getCartItems returns all items in the cart`() {
        val testCartItem = getTestCartItem(TEST_ID)
        whenever(cartRepository.findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)).thenReturn(getTestCart(TEST_ID) )
        whenever(cartItemService.findItemsInCart(TEST_ID)).thenReturn(listOf(testCartItem) )

        val items = shoppingService.getCartItems(TEST_ID, TEST_ID)

        assertEquals(1, items.size)
        assertEquals(testCartItem, items[0])

        verify(cartRepository, times(1)).findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)
        verify(cartItemService, times(1)).findItemsInCart(TEST_ID)
    }

    @Test
    fun `getCartTotal calculates the total price of the cart`() {
        whenever(cartRepository.findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)).thenReturn(getTestCart(TEST_ID) )
        whenever(cartItemService.findItemsInCart(TEST_ID)).thenReturn(listOf(getTestCartItem(TEST_ID)) )

        val total = shoppingService.getCartTotal(TEST_ID, TEST_ID)

        assertEquals(TEST_PRICE * TEST_QUANTITY, total)

        verify(cartRepository, times(1)).findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)
        verify(cartItemService, times(1)).findItemsInCart(TEST_ID)
    }

    @Test
    fun `checkoutCart sets the cart to checked out`() {
        val testCart = getTestCart(TEST_ID)
        whenever(cartRepository.findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)).thenReturn(testCart)

        shoppingService.checkoutCart(TEST_ID, TEST_ID)

        assertTrue(testCart.checkedOut)

        verify(cartRepository).save(testCart)
        verify(cartRepository, times(1)).findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)
    }

    @Test
    fun `addItemToCart throws CartNotFoundException when no active cart is found`() {
        whenever(cartRepository.findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)).thenReturn(null )

        assertThrows(NoActiveCartFoundForTheUserException::class.java) {
            shoppingService.addItemToCart(TEST_ID, TEST_ID, TEST_QUANTITY, TEST_ID)
        }
    }

    @Test
    fun `updateItemQuantity throws CartItemNotFoundException when item not found`() {
        whenever(cartItemService.findItemInCart(TEST_ID, TEST_ID)).doAnswer { throw CartItemNotFoundException("Message") }
        whenever(cartRepository.findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)).thenReturn(getTestCart(TEST_ID) )

        assertThrows(CartItemNotFoundException::class.java) {
            shoppingService.updateItemQuantity(TEST_ID, TEST_ID, TEST_QUANTITY, TEST_ID)
        }
    }

    @Test
    fun `getCartItems throws CartNotFoundException when no active cart is found`() {
        whenever(cartRepository.findByIdAndUserIdAndCheckedOutFalse(TEST_ID, TEST_ID)).thenReturn(null )

        assertThrows(NoActiveCartFoundForTheUserException::class.java) {
            shoppingService.getCartItems(TEST_ID, TEST_ID)
        }
    }
}