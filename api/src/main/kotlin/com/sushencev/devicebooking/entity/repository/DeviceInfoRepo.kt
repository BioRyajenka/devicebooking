package com.sushencev.devicebooking.entity.repository

import com.sushencev.devicebooking.entity.DeviceInfo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DeviceInfoRepo : JpaRepository<DeviceInfo, UUID>
