package `in`.sangeet.shopping.integration

import com.fasterxml.jackson.databind.ObjectMapper
import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_EMAIL
import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_ID
import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_NAME
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestUser
import `in`.sangeet.shopping.dto.UserDTO
import `in`.sangeet.shopping.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class UserTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @Transactional
    fun `create a new user for happy test case`() {
        val username = "newusername"
        val email = "newemail@gmail.com"
        val password = "pass"
        mockMvc.perform(
            post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UserDTO(username, email, password)))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.password").exists())
            .andExpect(jsonPath("$.password").value(password))
            .andExpect(jsonPath("$.username").exists())
            .andExpect(jsonPath("$.username").value(username))
    }

    @Test
    @Transactional
    fun `create a new user when user with given email already exists`() {
        userRepository.save(getTestUser(TEST_ID))
        mockMvc.perform(
            post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UserDTO("Sangeet", TEST_EMAIL, "Pass")))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    @Transactional
    fun `create a new user when user with given username already exists`() {
        userRepository.save(getTestUser(TEST_ID))
        mockMvc.perform(
            post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UserDTO(TEST_NAME, "email@gmail.com", "Pass")))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    @Transactional
    fun `create a new user when user with email already existing`() {
        userRepository.save(getTestUser(TEST_ID))
        mockMvc.perform(
            post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UserDTO("name", TEST_EMAIL, "Pass")))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    @Transactional
    fun `create a new user when user with invalid email`() {
        mockMvc.perform(
            post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UserDTO(TEST_NAME, "email", "")))
        )
            .andExpect(status().isBadRequest)
    }
}