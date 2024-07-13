package `in`.sangeet.shopping.service.impl

import `in`.sangeet.shopping.exceptions.UserNotFoundException
import `in`.sangeet.shopping.model.User
import `in`.sangeet.shopping.repository.UserRepository
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
        userRepository.findById(userId).orElseThrow { UserNotFoundException() }
}