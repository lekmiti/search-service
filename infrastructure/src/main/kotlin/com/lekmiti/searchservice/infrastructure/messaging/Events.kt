package com.lekmiti.searchservice.infrastructure.messaging

import java.time.LocalDateTime
import java.util.*


data class Event<T>(
    val metaData: EventMetaData?,
    val payload: T
)

data class EventMetaData(
    val eventID: UUID?,
    val eventType: String?,
    val eventDate: LocalDateTime?,
    val source: String?,
    val user: String?
)

