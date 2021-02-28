package com.lekmiti.searchservice.infrastructure.messaging

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.*


data class Event<T>(
    @JsonProperty("metaData")
    val metaDataEvent: MetaDataEvent,
    @JsonProperty("payload")
    val payload: T
)

data class MetaDataEvent(
    @JsonProperty("eventID")
    val eventID: UUID? = null,
    @JsonProperty("eventType")
    val eventType: String? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @JsonProperty("eventDateTime")
    val eventDateTime: Date? = Date(),
    @JsonProperty("source")
    val source: String? = null,
    @JsonProperty("user")
    val user: String? = null
)


