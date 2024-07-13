package `in`.sangeet.shopping.service

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
}