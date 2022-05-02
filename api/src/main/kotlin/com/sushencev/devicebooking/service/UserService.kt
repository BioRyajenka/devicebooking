package com.sushencev.devicebooking.service

import com.sushencev.devicebooking.dto.UserDto
import com.sushencev.devicebooking.entity.mapper.UserMapper
import com.sushencev.devicebooking.entity.repository.UserRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepo: UserRepo,
    private val userMapper: UserMapper,
) {
    fun getUser(userId: UUID): UserDto {
        val user = userRepo.getById(userId)
        return userMapper.toDto(user)
    }
}
