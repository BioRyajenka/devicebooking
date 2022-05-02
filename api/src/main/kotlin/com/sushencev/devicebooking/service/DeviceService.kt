package com.sushencev.devicebooking.service

import com.sushencev.devicebooking.dto.BookRecordDto
import com.sushencev.devicebooking.entity.BookRecord
import com.sushencev.devicebooking.entity.mapper.BookRecordMapper
import com.sushencev.devicebooking.entity.repository.BookRecordRepo
import com.sushencev.devicebooking.entity.repository.DeviceInfoRepo
import com.sushencev.devicebooking.type.BookStatus.AVAILABLE
import com.sushencev.devicebooking.type.BookStatus.BOOKED
import org.springframework.stereotype.Service
import java.time.Instant.now
import java.util.*

@Service
class DeviceService(
    private val bookRecordRepo: BookRecordRepo,
    private val deviceInfoRepo: DeviceInfoRepo,
    private val bookRecordMapper: BookRecordMapper,
) {
    fun bookDevice(deviceId: UUID, userId: UUID): BookRecordDto {
        require(deviceInfoRepo.existsById(deviceId)) {
            "Unknown device"
        }

        val lastBookRecord = bookRecordRepo.findByUserIdAndDeviceId(userId, deviceId)
        require(lastBookRecord == null || lastBookRecord.statusChange === AVAILABLE) {
            "Device is already in use"
        }

        val newRecord = BookRecord(
            userId = userId,
            deviceId = deviceId,
            statusChange = BOOKED,
            date = now(),
        )
        val savedRecord = bookRecordRepo.save(newRecord)

        return bookRecordMapper.toDto(savedRecord)
    }
}
