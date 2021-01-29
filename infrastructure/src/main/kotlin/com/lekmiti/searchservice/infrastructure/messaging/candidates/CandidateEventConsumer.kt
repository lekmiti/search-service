package com.lekmiti.searchservice.infrastructure.messaging.candidates

import com.lekmiti.searchservice.infrastructure.messaging.Event
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


private val log = LoggerFactory.getLogger(CandidateEventConsumer::class.java)

@Component
@Transactional
class CandidateEventConsumer {

    @StreamListener("candidate-created")
    fun onCandidateCreated(@Payload payload: Event<CandidatePayload>) {
        log.info("new candidate created event received $payload")
    }

    @StreamListener("candidate-updated")
    fun onCandidateUpdated(@Payload payload: Event<CandidatePayload>) {
        log.info("new candidate created event received $payload")
    }
}