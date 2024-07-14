package `in`.sangeet.shopping.service

import `in`.sangeet.shopping.dto.UserDTO
import `in`.sangeet.shopping.exceptions.UserNotFoundException
import `in`.sangeet.shopping.exceptions.UserAlreadyExistsException
import `in`.sangeet.shopping.model.User

interface UserService {

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user corresponding to the given ID.
     * @throws UserNotFoundException if the user is not found.
     */
    fun getUserById(userId: Long): User

    /**
     * Retrieves a user by their ID.
     *
     * @param userDTO The DTO containing details of the user.
     * @return The new user created.
     * @throws UserAlreadyExistsException if the user is not found.
     */
    fun createUser(userDTO: UserDTO): User
}