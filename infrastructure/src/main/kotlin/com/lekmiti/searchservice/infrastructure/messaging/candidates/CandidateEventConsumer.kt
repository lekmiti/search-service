package com.lekmiti.searchservice.infrastructure.messaging.candidates

import com.lekmiti.searchservice.application.CandidateAppService
import com.lekmiti.searchservice.infrastructure.messaging.Event
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


private val log = LoggerFactory.getLogger(CandidateEventConsumer::class.java)

@Component
@Transactional
class CandidateEventConsumer(private val candidateAppService: CandidateAppService) {

    @StreamListener("candidate-created")
   fun onCandidateCreated(@Payload candidateCreatedEvent: Event<CandidatePayload>) {
        log.info("candidate-created event received $candidateCreatedEvent")
        candidateAppService.saveOrUpdateCandidate(candidateCreatedEvent.payload.toCandidateUpsert())
    }

    @StreamListener("candidate-updated")
    fun onCandidateUpdated(@Payload candidateUpdatedEvent: Event<CandidatePayload>) {
        log.info("candidate-updated event received $candidateUpdatedEvent")
        candidateAppService.saveOrUpdateCandidate(candidateUpdatedEvent.payload.toCandidateUpsert())
    }

    @StreamListener("candidate-data-deleted")
    fun onCandidateDataDeleted(@Payload candidateDataDeletedEvent: Event<CandidatePayload>) {
        log.info("candidate-data-deleted event received $candidateDataDeletedEvent")
        val candidateDelete =
            candidateDataDeletedEvent.payload.toCandidateDelete(candidateDataDeletedEvent.metaData.eventType)
        candidateAppService.deleteCandidateData(candidateDelete)
    }
}