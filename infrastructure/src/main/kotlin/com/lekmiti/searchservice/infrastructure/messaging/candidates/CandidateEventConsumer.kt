package com.lekmiti.searchservice.infrastructure.messaging.candidates

import com.lekmiti.searchservice.infrastructure.messaging.Event
import com.lekmiti.searchservice.usecases.CandidateUseCases
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


private val log = LoggerFactory.getLogger(CandidateEventConsumer::class.java)

@Component
@Transactional
class CandidateEventConsumer(private val candidateUseCases: CandidateUseCases) {

    @StreamListener("candidate-created")
   fun onCandidateCreated(@Payload candidateCreatedEvent: Event<CandidatePayload>) {
        log.info("candidate-created event received $candidateCreatedEvent")
        candidateUseCases.createOrUpdateCandidate(candidateCreatedEvent.payload.toCandidateUpsert())
    }

    @StreamListener("candidate-updated")
    fun onCandidateUpdated(@Payload candidateUpdatedEvent: Event<CandidatePayload>) {
        log.info("candidate-updated event received $candidateUpdatedEvent")
        candidateUseCases.createOrUpdateCandidate(candidateUpdatedEvent.payload.toCandidateUpsert())
    }

    @StreamListener("candidate-data-deleted")
    fun onCandidateDataDeleted(@Payload candidateDataDeletedEvent: Event<CandidatePayload>) {
        log.info("candidate-data-deleted event received $candidateDataDeletedEvent")
        val candidateDelete =
            candidateDataDeletedEvent.payload.toCandidateDelete(candidateDataDeletedEvent.metaDataEvent.eventType)
        candidateUseCases.deleteCandidateData(candidateDelete)
    }
}