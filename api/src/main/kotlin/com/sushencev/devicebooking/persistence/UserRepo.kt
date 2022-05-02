package com.sushencev.devicebooking.persistence

import com.sushencev.devicebooking.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepo : JpaRepository<User, UUID> {
    fun findByName(name: String): User?
}
