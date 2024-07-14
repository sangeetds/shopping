package `in`.sangeet.shopping.repository

import `in`.sangeet.shopping.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {

    fun findByName(name: String): Product?
}