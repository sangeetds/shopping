package `in`.sangeet.shopping.repository

import `in`.sangeet.shopping.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>