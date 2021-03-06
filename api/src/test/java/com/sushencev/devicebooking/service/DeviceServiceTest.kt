package com.sushencev.devicebooking.service

import com.sushencev.devicebooking.entity.BookRecord
import com.sushencev.devicebooking.entity.mapper.BookRecordMapper
import com.sushencev.devicebooking.entity.repository.BookRecordRepo
import com.sushencev.devicebooking.entity.repository.DeviceInfoRepo
import com.sushencev.devicebooking.exception.DeviceNotFoundException
import com.sushencev.devicebooking.service.devicedata.DeviceDataService
import com.sushencev.devicebooking.type.BookStatus.AVAILABLE
import com.sushencev.devicebooking.type.BookStatus.BOOKED
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.time.Instant.now
import java.util.UUID.randomUUID

internal class DeviceServiceTest {
    lateinit var bookRecordRepo: BookRecordRepo
    lateinit var deviceInfoRepo: DeviceInfoRepo
    lateinit var deviceService: DeviceService
    lateinit var deviceDataService: DeviceDataService
    lateinit var bookRecordMapper: BookRecordMapper

    val deviceId = randomUUID()
    val userId = randomUUID()

    @BeforeEach
    fun setUp() {
        bookRecordRepo = mock()
        deviceInfoRepo = mock()
        bookRecordMapper = mock()
        deviceDataService = mock()

        given { deviceInfoRepo.existsById(deviceId) }.willReturn(true)

        deviceService = DeviceService(bookRecordRepo, deviceInfoRepo, bookRecordMapper, deviceDataService)
    }

    @Test
    fun `bookDevice() should fail on unknown device`() {
        val unknownDeviceId = randomUUID()

        given { deviceInfoRepo.existsById(unknownDeviceId) }.willReturn(false)

        assertThrows<DeviceNotFoundException> {
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

    @Test
    fun `returnDevice() should do nothing if device is not booked`() {
        given { bookRecordRepo.findFirstByDeviceIdOrderByDateDesc(deviceId) }
            .willReturn(BookRecord(userId, deviceId, now(), AVAILABLE))

        deviceService.returnDevice(deviceId, userId)

        verify(bookRecordRepo).findFirstByDeviceIdOrderByDateDesc(deviceId)
        verifyNoMoreInteractions(bookRecordRepo)
    }

    @Test
    fun `returnDevice() should create return record if device is booked`() {
        given { bookRecordRepo.findFirstByDeviceIdOrderByDateDesc(deviceId) }
            .willReturn(BookRecord(userId, deviceId, now(), BOOKED))

        deviceService.returnDevice(deviceId, userId)

        verify(bookRecordRepo).findFirstByDeviceIdOrderByDateDesc(deviceId)
        verify(bookRecordRepo).save(any())
    }
}
