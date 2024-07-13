package `in`.sangeet.shopping.service

import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_ID
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestProduct
import `in`.sangeet.shopping.exceptions.ProductNotFoundException
import `in`.sangeet.shopping.repository.ProductRepository
import `in`.sangeet.shopping.service.impl.ProductServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class ProductServiceImplTest {

    private val productRepository: ProductRepository = mock()
    private lateinit var  productService: ProductService

    @BeforeEach
    fun setup() {
        productService = ProductServiceImpl(productRepository)
    }

    @Test
    fun `getProductById returns product when found`() {
        val product = getTestProduct(TEST_ID)
        whenever(productRepository.findById(TEST_ID)).thenReturn(Optional.of(product))

        val result = productService.getProductById(TEST_ID)

        assertEquals(product, result)
    }

    @Test
    fun `getProductById throws exception when product not found`() {
        whenever(productRepository.findById(TEST_ID)).thenReturn(Optional.empty())

        assertThrows<ProductNotFoundException> {
            productService.getProductById(TEST_ID)
        }
    }
}