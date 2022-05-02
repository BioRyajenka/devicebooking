package com.sushencev.devicebooking.exception

import java.util.UUID

class DeviceNotFoundException(deviceId: UUID): RuntimeException("Device $deviceId not found")
