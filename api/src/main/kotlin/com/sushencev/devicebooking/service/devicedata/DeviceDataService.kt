package com.sushencev.devicebooking.service.devicedata


interface DeviceDataService {
    fun getDeviceData(deviceRequest: String): DeviceTechnicalData
}
