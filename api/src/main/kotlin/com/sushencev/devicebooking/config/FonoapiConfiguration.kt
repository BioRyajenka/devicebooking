package com.sushencev.devicebooking.config

import com.sushencev.devicebooking.service.devicedata.DeviceTechnicalData
import com.sushencev.devicebooking.type.DeviceFeature
import com.sushencev.devicebooking.type.DeviceFeature.*
import fonoapi.FonoapiFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FonoapiConfiguration {
    @Bean
    fun initialCacheContent() = mapOf(
        "Samsung Galaxy S8" to DeviceTechnicalData("techonology 1", listOf(BANDS_2G)),
        "Samsung Galaxy S9" to DeviceTechnicalData("techonology 2", listOf(BANDS_3G)),
        "Motorola Nexus 6" to DeviceTechnicalData("techonology 3", listOf()),
        "Oneplus 9" to DeviceTechnicalData("techonology 4", listOf(BANDS_2G)),
        "Apple iPhone 13" to DeviceTechnicalData("techonology 17", listOf(BANDS_2G, BANDS_3G)),
        "Apple iPhone 12" to DeviceTechnicalData("techonology 9", listOf(BANDS_3G)),
        "Apple iPhone 11" to DeviceTechnicalData("techonology 13", listOf()),
        "iPhone X" to DeviceTechnicalData("techonology 16", listOf(BANDS_4G)),
        "Nokia 3310" to DeviceTechnicalData("techonology 11", listOf()),
    )

    @Bean
    fun fonoapiService() = FonoapiFactory.create()
}
