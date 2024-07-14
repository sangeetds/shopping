package `in`.sangeet.shopping.service.impl

import `in`.sangeet.shopping.dto.ProductDTO
import `in`.sangeet.shopping.exceptions.ProductAlreadyExistsException
import `in`.sangeet.shopping.exceptions.ProductDetailsNotCorrectException
import `in`.sangeet.shopping.exceptions.ProductNotFoundException
import `in`.sangeet.shopping.model.Product
import `in`.sangeet.shopping.repository.ProductRepository
import `in`.sangeet.shopping.service.ProductService
import org.springframework.stereotype.Service

/**
 * Service class for handling operations related to products.
 *
 * @property productRepository Repository for accessing product data.
 */
@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

    override fun getProductById(productId: Long): Product =
        productRepository.findById(productId).orElseThrow { ProductNotFoundException("Product not found in the cart") }

    override fun createProduct(productDTO: ProductDTO): Product {
        val newProduct = validateProductData(productDTO)
        return productRepository.save(newProduct)
    }

    private fun validateProductData(productDTO: ProductDTO): Product {
        val (name, description, price) = productDTO
        if (name.isBlank() || description.isBlank() || price == 0.0) {
            throw ProductDetailsNotCorrectException()
        }
        val newProduct = productRepository.findByName(name)

        newProduct?.let { throw ProductAlreadyExistsException() }

        return Product(description = description, name = name, price = price)
    }
}