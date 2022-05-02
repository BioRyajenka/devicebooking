package com.sushencev.devicebooking.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sushencev.devicebooking.dto.BookRecordDto
import com.sushencev.devicebooking.dto.DeviceDto
import com.sushencev.devicebooking.type.BookStatus.AVAILABLE
import com.sushencev.devicebooking.type.BookStatus.BOOKED
import com.sushencev.devicebooking.type.DeviceFeature.BANDS_2G
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*
import java.util.UUID.randomUUID

@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class TestDevices : BaseIntegrationTest() {
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `device listing returns test data`() {
        assertThat(listDevices())
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactlyInAnyOrder(
                DeviceDto(
                    id = randomUUID(),
                    name = "iphone",
                    booking = BookRecordDto(AVAILABLE, null, null),
                    technology = "iphone tech",
                    features = listOf(BANDS_2G),
                ),
                DeviceDto(
                    id = randomUUID(),
                    name = "xiaomi",
                    booking = BookRecordDto(AVAILABLE, null, null),
                    technology = "xiaomi tech",
                    features = emptyList(),
                ),
            )
    }

    @Test
    fun `should be possible to book and return device`() {
        val device = listDevices().first()

        bookDevice(device.id).also {
            assertEquals(BOOKED, it.status)
        }
        assertEquals(BOOKED, getDevice(device.id).booking.status)

        returnDevice(device.id)
        assertEquals(AVAILABLE, getDevice(device.id).booking.status)
    }

    private fun returnDevice(deviceId: UUID) {
        mockMvc.perform(
            post("/api/v1/devices/$deviceId/return")
        ).andReturn()
    }

    private fun bookDevice(deviceId: UUID): BookRecordDto {
        val stringResponse = mockMvc.perform(
            post("/api/v1/devices/$deviceId/book")
        ).andReturn().response.getContentAsString(UTF_8)

        return objectMapper.readValue(stringResponse)
    }

    private fun getDevice(deviceId: UUID): DeviceDto {
        return listDevices().find { it.id == deviceId }!!
    }

    private fun listDevices(): List<DeviceDto> {
        val stringResponse = mockMvc.perform(
            get("/api/v1/devices")
        ).andReturn().response.getContentAsString(UTF_8)

        return objectMapper.readValue(stringResponse)
    }
}
