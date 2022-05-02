package com.sushencev.devicebooking.entity.repository

import com.sushencev.devicebooking.entity.BookRecord
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRecordRepo : JpaRepository<BookRecord, UUID> {
    fun findByUserIdAndDeviceId(userId: UUID, deviceId: UUID): BookRecord?
}
