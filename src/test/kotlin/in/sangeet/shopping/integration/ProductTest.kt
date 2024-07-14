package `in`.sangeet.shopping.integration

import com.fasterxml.jackson.databind.ObjectMapper
import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_EMAIL
import `in`.sangeet.shopping.constants.TestConstants.Companion.TEST_NAME
import `in`.sangeet.shopping.constants.TestConstants.Companion.getTestProduct
import `in`.sangeet.shopping.dto.ProductDTO
import `in`.sangeet.shopping.repository.ProductRepository
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
class ProductTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Test
    @Transactional
    fun `create a new product for happy test case`() {
        val name = "uniquename"
        val description = "some basic description"
        val price = 10.0
        mockMvc.perform(
            post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ProductDTO(name, description, price)))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.description").exists())
            .andExpect(jsonPath("$.description").value(description))
            .andExpect(jsonPath("$.price").exists())
            .andExpect(jsonPath("$.price").value(price))
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.name").value(name))
    }

    @Test
    @Transactional
    fun `create a new product when product with given name already exists`() {
        productRepository.save(getTestProduct())
        mockMvc.perform(
            post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ProductDTO(TEST_NAME, "description", 0.0)))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    @Transactional
    fun `create a new product when product with price is invalid`() {
        mockMvc.perform(
            post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ProductDTO("name", TEST_EMAIL, 0.0)))
        )
            .andExpect(status().isBadRequest)
    }
}