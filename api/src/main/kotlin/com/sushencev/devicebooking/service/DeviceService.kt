package com.sushencev.devicebooking.service

import com.sushencev.devicebooking.dto.BookRecordDto
import com.sushencev.devicebooking.entity.BookRecord
import com.sushencev.devicebooking.entity.mapper.BookRecordMapper
import com.sushencev.devicebooking.entity.repository.BookRecordRepo
import com.sushencev.devicebooking.entity.repository.DeviceInfoRepo
import com.sushencev.devicebooking.exception.DeviceNotFoundException
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
        ensureDeviceExists(deviceId)

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

    fun returnDevice(deviceId: UUID, userId: UUID) {
        ensureDeviceExists(deviceId)

        val lastBookingRecord = bookRecordRepo.findFirstByDeviceIdOrderByDateDesc(deviceId)
        if (lastBookingRecord == null || lastBookingRecord.statusChange === AVAILABLE) {
            return
        }

        val newRecord = BookRecord(
            userId = userId,
            deviceId = deviceId,
            statusChange = AVAILABLE,
            date = now(),
        )
        bookRecordRepo.save(newRecord)
    }

    private fun ensureDeviceExists(deviceId: UUID) {
        if (!deviceInfoRepo.existsById(deviceId)) {
            throw DeviceNotFoundException(deviceId)
        }
    }
}
