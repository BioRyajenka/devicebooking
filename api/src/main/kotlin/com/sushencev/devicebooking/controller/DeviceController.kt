package com.sushencev.devicebooking.controller

import com.sushencev.devicebooking.dto.BookRecordDto
import com.sushencev.devicebooking.dto.DeviceDto
import com.sushencev.devicebooking.service.DeviceService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/v1/devices")
class DeviceController(
    private val deviceService: DeviceService,
) {

    @Operation(summary = "Book the device")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful operation"),
        ApiResponse(responseCode = "404", description = "Device not found"),
    ])
    @PostMapping("/{device-id}/book")
    fun bookDevice(
        @PathVariable("device-id") deviceId: UUID,
        @CurrentSecurityContext(expression="authentication?.principal?.id") userId: UUID,
    ): BookRecordDto {
        return deviceService.bookDevice(deviceId, userId)
    }

    @Operation(summary = "Return the device")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful operation or device was not booked"),
        ApiResponse(responseCode = "404", description = "Device not found"),
    ])
    @PostMapping("/{device-id}/return")
    fun returnDevice(
        @PathVariable("device-id") deviceId: UUID,
        @CurrentSecurityContext(expression="authentication?.principal?.id") userId: UUID,
    ) {
        deviceService.returnDevice(deviceId, userId)
    }

    @Operation(summary = "Get the list of devices")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successful operation"),
    ])
    @GetMapping
    fun getDevices(): List<DeviceDto> {
        TODO()
    }
}
