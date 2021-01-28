package com.lekmiti.searchservice.infrastructure.messaging

import com.lekmiti.searchservice.domain.CandidateCode
import com.lekmiti.searchservice.domain.Cv
import com.lekmiti.searchservice.domain.SocialNetworks
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
        val user: String?)


data class CandidatePayload(
        val candidateCode: CandidateCode,
        val firstName: String?,
        val phoneNumber: String?,
        val socialNetworks: SocialNetworks,
        val lastName: String?,
        val email: String?,
        val cv: Cv?)
