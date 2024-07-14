package `in`.sangeet.shopping.controller

import `in`.sangeet.shopping.dto.UserDTO
import `in`.sangeet.shopping.model.User
import `in`.sangeet.shopping.service.UserService
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
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    /**
     * Creates a new user.
     *
     * @param userDTO The DTO containing all info of the user.
     * @return The created user.
     */
    @Operation(summary = "Creates a new user for user")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "New user created",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = User::class))]
            ),
            ApiResponse(responseCode = "400", description = "User already exists", content = [Content()]),
            ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
        ]
    )
    @PostMapping
    fun createUser(@RequestBody userDTO: UserDTO): User =
        userService.createUser(userDTO)

}
