package `in`.sangeet.shopping.service

import `in`.sangeet.shopping.exceptions.UserNotFoundException
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
}