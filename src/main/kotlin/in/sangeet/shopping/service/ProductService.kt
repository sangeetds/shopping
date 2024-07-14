package `in`.sangeet.shopping.service

import `in`.sangeet.shopping.dto.ProductDTO
import `in`.sangeet.shopping.exceptions.UserAlreadyExistsException
import `in`.sangeet.shopping.model.Product

interface ProductService {

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The product corresponding to the given ID.
     * @throws RuntimeException if the product is not found.
     */
    fun getProductById(productId: Long): Product

    /**
     * Retrieves a product by their ID.
     *
     * @param productDTO The DTO containing details of the product.
     * @return The new product created.
     * @throws UserAlreadyExistsException if the product is not found.
     */
    fun createProduct(productDTO: ProductDTO): Product
}