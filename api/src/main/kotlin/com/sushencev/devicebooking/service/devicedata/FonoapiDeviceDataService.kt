package com.sushencev.devicebooking.service.devicedata

import com.sushencev.devicebooking.exception.DeviceNotFetchedException
import com.sushencev.devicebooking.service.devicedata.fonoapi.FonoApiService
import com.sushencev.devicebooking.type.DeviceFeature.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service

@Service
@PropertySource("classpath:application.properties")
class FonoapiDeviceDataService(
    @Value("\${fonoapi.token}") private val fonoapiToken: String,
    private val fonoApiService: FonoApiService,
): DeviceDataService {

    override fun getDeviceData(deviceRequest: String): DeviceTechnicalData {
        val fonoapiDevice = fonoApiService.getDevice(fonoapiToken, deviceRequest).execute()

        if (!fonoapiDevice.isSuccessful) {
            throw DeviceNotFetchedException(deviceRequest)
        }

        val device = fonoapiDevice.body()?.singleOrNull() ?: throw DeviceNotFetchedException(deviceRequest)

        return DeviceTechnicalData(
            technology = device.technology,
            features = listOfNotNull(
                if (device._2g_bands == "Yes") BANDS_2G else null,
                if (device._3g_bands == "Yes") BANDS_3G else null,
                if (device._4g_bands == "Yes") BANDS_4G else null,
            )
        )
    }
}
