package com.sushencev.devicebooking.entity.mapper

import com.sushencev.devicebooking.dto.UserDto
import com.sushencev.devicebooking.entity.User
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    abstract fun toDto(entity: User): UserDto
}
