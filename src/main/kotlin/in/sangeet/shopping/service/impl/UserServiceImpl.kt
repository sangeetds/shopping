package `in`.sangeet.shopping.service.impl

import `in`.sangeet.shopping.dto.UserDTO
import `in`.sangeet.shopping.exceptions.UserAlreadyExistsException
import `in`.sangeet.shopping.exceptions.UserNotFoundException
import `in`.sangeet.shopping.model.User
import `in`.sangeet.shopping.repository.UserRepository
import `in`.sangeet.shopping.exceptions.UserDetailsNotCorrectException
import `in`.sangeet.shopping.service.UserService
import org.springframework.stereotype.Service

/**
 * Service class for handling operations related to users.
 *
 * @property userRepository Repository for accessing user data.
 */
@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun getUserById(userId: Long): User =
        userRepository.findById(userId).orElseThrow { UserNotFoundException("User not found.") }


    override fun createUser(userDTO: UserDTO): User {
        val newUser = validateUserData(userDTO)
        return userRepository.save(newUser)
    }

    private fun validateUserData(userDTO: UserDTO): User {
        val (username, email, password) = userDTO
        if (username.isBlank() || email.isBlank() || password.isBlank() || email.isInvalidEmail()) {
            throw UserDetailsNotCorrectException()
        }
        val newUser = userRepository.findByEmailOrUsername(email, username)

        newUser?.let { throw UserAlreadyExistsException() }

        return User(email = email, username = username, password = password)
    }

    fun String.isInvalidEmail(): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()
        return !this.matches(emailRegex)
    }
}