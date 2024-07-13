package `in`.sangeet.shopping.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.Max

@Entity(name = "users")
@Schema(description = "The user for which the shopping cart is made.")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user.", example = "123", required = true)
    val id: Long? = null,

    @Schema(description = "Username of the user.", example = "john_doe", required = true)
    @Max(50)
    val username: String,

    @Schema(description = "Password of the user.", example = "securepassword", required = true)
    @Max(50)
    val email: String,

    @Schema(description = "Email address of the user.", example = "john.doe@example.com", required = true)
    @Max(50)
    val password: String
)

