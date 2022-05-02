package com.sushencev.devicebooking.exception

import java.util.UUID

class DeviceNotFoundException(deviceId: UUID): RuntimeException("Device $deviceId not found")

class DeviceNotFetchedException(device: String): RuntimeException("Device $device technical details cannot be fetched")
