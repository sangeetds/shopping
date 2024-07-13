package `in`.sangeet.shopping.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Max

@Entity(name = "products")
@Schema(description = "The product that the user is buying")
data class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the product.", example = "1", required = true)
    val id: Long? = null,

    @Max(100)
    @Schema(description = "Name of the product.", example = "Laptop", required = true)
    val name: String,

    @Schema(description = "Description of the product.", example = "A high-end gaming laptop.", required = true)
    val description: String,

    @Schema(description = "Price of the product.", example = "1200.00", required = true)
    val price: Double
)