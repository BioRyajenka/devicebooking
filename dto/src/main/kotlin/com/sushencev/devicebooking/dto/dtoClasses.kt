package com.sushencev.devicebooking.dto

import com.sushencev.devicebooking.type.BookStatus
import com.sushencev.devicebooking.type.DeviceFeature
import java.time.Instant
import java.util.*

class UserDto(
    val id: UUID,
    val name: String,
)

class BookRecordDto(
    val status: BookStatus,
    val dateOfBooking: Instant?,
    val user: UserDto?,
)

class DeviceDto(
    val id: UUID,
    val name: String,
    val booking: BookRecordDto,
    val technology: String,
    val features: List<DeviceFeature>,
)
