package `in`.sangeet.shopping.service

import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_ID
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestUserDTO
import `in`.sangeet.shopping.exceptions.UserNotFoundException
import `in`.sangeet.shopping.repository.UserRepository
import `in`.sangeet.shopping.service.impl.UserServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class UserServiceImplTest {

    private val userRepository: UserRepository = mock<UserRepository>()

    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        userService = UserServiceImpl(userRepository)
    }

    @Test
    fun `getUserById returns user when found`() {
        val user = getTestUserDTO(TEST_ID)
        whenever(userRepository.findById(TEST_ID)).thenReturn(Optional.of(user))

        val result = userService.getUserById(TEST_ID)

        assertEquals(user, result)
    }

    @Test
    fun `getUserById throws exception when user not found`() {
        whenever(userRepository.findById(TEST_ID)).thenReturn(Optional.empty())

        assertThrows<UserNotFoundException> {
            userService.getUserById(TEST_ID)
        }
    }
}