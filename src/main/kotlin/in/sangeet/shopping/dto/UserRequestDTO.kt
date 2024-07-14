package `in`.sangeet.shopping.dto

data class UserRequestDTO(
    val userId: Long,
    val cartId: Long,
    val productId: Long,
    val quantity: Int
)
