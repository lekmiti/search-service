package com.lekmiti.searchservice.infrastructure.messaging

import com.lekmiti.searchservice.domain.CandidateCode
import com.lekmiti.searchservice.domain.Cv
import java.time.LocalDateTime
import java.util.*


data class Event<T>(
        val metaData: EventMetaData?,
        val payload: T
)

data class EventMetaData(
        val eventID: UUID? = null,
        val eventType: String? = "unknown-type",
        val eventDate: LocalDateTime? = null,
        val source: String? = "unknown-source",
        val user: String? = "unknown-user")


data class CandidatePayload(
        val candidateNbr: CandidateCode,
        val firstName: String? = null,
        val lastName: String? = null,
        val email: String? = null,
        val cv: Cv? = null)
