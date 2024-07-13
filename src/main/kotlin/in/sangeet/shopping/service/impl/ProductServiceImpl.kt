package `in`.sangeet.shopping.service.impl

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
        productRepository.findById(productId).orElseThrow { ProductNotFoundException() }
}