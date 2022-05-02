package com.sushencev.devicebooking.service.devicedata

import com.sushencev.devicebooking.type.DeviceFeature

data class DeviceTechnicalData(
    val technology: String,
    val features: List<DeviceFeature>,
)
