package com.lekmiti.searchservice.infrastructure.messaging

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*


data class Event<T>(
    @JsonProperty("metaData")
    val metaData: EventMetaData,
    @JsonProperty("payload")
    val payload: T
)

data class EventMetaData(
    @JsonProperty("eventID")
    val eventID: UUID?,
    @JsonProperty("eventType")
    val eventType: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @JsonProperty("eventDateTime")
    val eventDateTime: Date?,
    @JsonProperty("source")
    val source: String?,
    @JsonProperty("user")
    val user: String?
)


