package com.sushencev.devicebooking.service

import com.sushencev.devicebooking.entity.BookRecord
import com.sushencev.devicebooking.entity.mapper.BookRecordMapper
import com.sushencev.devicebooking.entity.repository.BookRecordRepo
import com.sushencev.devicebooking.entity.repository.DeviceInfoRepo
import com.sushencev.devicebooking.type.BookStatus.BOOKED
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.Instant.now
import java.util.UUID.randomUUID

internal class DeviceServiceTest {
    lateinit var bookRecordRepo: BookRecordRepo
    lateinit var deviceInfoRepo: DeviceInfoRepo
    lateinit var deviceService: DeviceService
    lateinit var bookRecordMapper: BookRecordMapper

    val deviceId = randomUUID()
    val userId = randomUUID()

    @BeforeEach
    fun setUp() {
        bookRecordRepo = mock()
        deviceInfoRepo = mock()
        bookRecordMapper = mock()

        given { deviceInfoRepo.existsById(deviceId) }.willReturn(true)

        deviceService = DeviceService(bookRecordRepo, deviceInfoRepo, bookRecordMapper)
    }

    @Test
    fun `bookDevice() should fail on unknown device`() {
        val unknownDeviceId = randomUUID()

        given { deviceInfoRepo.existsById(unknownDeviceId) }.willReturn(false)

        assertThrows<IllegalArgumentException> {
            deviceService.bookDevice(unknownDeviceId, userId)
        }
    }

    @Test
    fun `bookDevice() should fail if device is in use`() {
        given(bookRecordRepo.findByUserIdAndDeviceId(userId, deviceId))
            .willReturn(BookRecord(userId, deviceId, now(), BOOKED))

        assertThrows<IllegalArgumentException> {
            deviceService.bookDevice(deviceId, userId)
        }
    }

    @Test
    fun `bookDevice() should create book record if device is available`() {
        given { bookRecordRepo.save(any()) }
            .willReturn(BookRecord(userId, deviceId, now(), BOOKED))

        deviceService.bookDevice(deviceId, userId)

        verify(bookRecordRepo).save(any())
    }
}
