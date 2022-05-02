package com.sushencev.devicebooking.service

import com.sushencev.devicebooking.entity.User
import com.sushencev.devicebooking.entity.mapper.UserMapper
import com.sushencev.devicebooking.entity.repository.UserRepo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.util.*
import java.util.UUID.randomUUID

internal class UserServiceTest {
    lateinit var userRepo: UserRepo
    lateinit var userService: UserService
    lateinit var userMapper: UserMapper

    val userId = randomUUID()

    @BeforeEach
    fun setUp() {
        userRepo = mock()
        userMapper = mock()

        userService = UserService(userRepo, userMapper)
    }

    @Test
    fun `getUser() should use repo`() {
        given { userRepo.getById(userId) }.willReturn(User("name", "pass"))

        userService.getUser(userId)

        verify(userRepo).getById(userId)
    }
}
