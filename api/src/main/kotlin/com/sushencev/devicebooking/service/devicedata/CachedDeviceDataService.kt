package com.sushencev.devicebooking.service.devicedata

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CachedDeviceDataService(
    @Qualifier("fonoapiDeviceDataService") private val nestedService: DeviceDataService,
    initialCacheContent: Map<String, DeviceTechnicalData>,
): DeviceDataService {
    private val cache = mutableMapOf<String, DeviceTechnicalData>()

    init {
        cache += initialCacheContent
    }

    override fun getDeviceData(deviceRequest: String): DeviceTechnicalData {
        return cache.getOrPut(deviceRequest) {
            nestedService.getDeviceData(deviceRequest)
        }
    }
}
