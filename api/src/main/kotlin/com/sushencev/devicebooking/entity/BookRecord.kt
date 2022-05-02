package com.sushencev.devicebooking.entity

import com.sushencev.devicebooking.type.BookStatus
import org.hibernate.annotations.GenericGenerator
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "book_records")
class BookRecord(
    var userId: UUID,
    var deviceId: UUID,

    var date: Instant,

    var statusChange: BookStatus,

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null,
)
