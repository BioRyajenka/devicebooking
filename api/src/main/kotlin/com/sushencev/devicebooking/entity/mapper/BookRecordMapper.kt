package com.sushencev.devicebooking.entity.mapper

import com.sushencev.devicebooking.dto.BookRecordDto
import com.sushencev.devicebooking.entity.BookRecord
import com.sushencev.devicebooking.service.UserService
import com.sushencev.devicebooking.type.BookStatus
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.beans.factory.annotation.Autowired

@Mapper(
    componentModel = "spring",
    imports = [ BookStatus::class ]
)
abstract class BookRecordMapper {
    @Autowired
    protected lateinit var userService: UserService

    @Mapping(target = "status", source = "statusChange")
    @Mapping(target = "dateOfBooking", expression = "java(entity.getStatusChange() == BookStatus.BOOKED ? entity.getDate() : null)")
    @Mapping(target = "user", expression = "java(entity.getStatusChange() == BookStatus.BOOKED ? userService.getUser(entity.getUserId()) : null)")
    abstract fun toDto(entity: BookRecord): BookRecordDto
}
