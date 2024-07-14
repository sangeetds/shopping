package `in`.sangeet.shopping.controller

import `in`.sangeet.shopping.dto.ProductDTO
import `in`.sangeet.shopping.model.Product
import `in`.sangeet.shopping.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(private val productService: ProductService) {

    /**
     * Creates a new product.
     *
     * @param productDTO The DTO containing all info of the product.
     * @return The created product.
     */
    @Operation(summary = "Creates a new product for product")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "New product created",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Product::class))]
            ),
            ApiResponse(responseCode = "400", description = "User already exists", content = [Content()]),
            ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
        ]
    )
    @PostMapping
    fun createUser(@RequestBody productDTO: ProductDTO): Product =
        productService.createProduct(productDTO)

}