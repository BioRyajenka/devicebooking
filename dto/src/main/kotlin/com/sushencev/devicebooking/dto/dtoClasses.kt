package com.sushencev.devicebooking.dto

import com.sushencev.devicebooking.type.BookStatus
import com.sushencev.devicebooking.type.DeviceFeature
import java.time.Instant
import java.util.*

class UserDto(
    val id: UUID,
    val name: String,
)

class BookStatusDto(
    val status: BookStatus,
    val dateOfBooking: Instant?,
    val whoBooked: UserDto?,
)

class DeviceDto(
    val id: UUID,
    val bookStatus: BookStatusDto,
    val technology: String,
    val features: List<DeviceFeature>,
)
