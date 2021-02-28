package com.lekmiti.searchservice.infrastructure.messaging.candidates

import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.candidate.CandidateDelete
import com.lekmiti.searchservice.infrastructure.messaging.Event
import com.lekmiti.searchservice.infrastructure.messaging.MetaDataEvent
import com.lekmiti.searchservice.usecases.CandidateUseCases
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class CandidateEventConsumerTest {

    private val candidateUseCases = mockk<CandidateUseCases>(relaxed = true)

    private val candidateEventConsumer = CandidateEventConsumer(candidateUseCases)

    @Test
    fun `should index candidate on candidate created event`() {

        val candidateCreatedEvent = Event(
            metaDataEvent = MetaDataEvent(eventType = "candidate_created"),
            payload = CandidatePayload(candidateCode = "C1", company = "zsoft-consulting")
        )

        candidateEventConsumer.onCandidateCreated(candidateCreatedEvent)

        verify {
            candidateUseCases.createOrUpdateCandidate(Candidate(candidateCode = "C1", company = "zsoft-consulting"))
        }

    }

    @Test
    fun `should re-index candidate on candidate updated event`() {

        val candidateUpdatedEvent = Event(
            metaDataEvent = MetaDataEvent(eventType = "candidate_updated"),
            payload = CandidatePayload(candidateCode = "C1", company = "zsoft-consulting")
        )

        candidateEventConsumer.onCandidateUpdated(candidateUpdatedEvent)

        verify {
            candidateUseCases.createOrUpdateCandidate(Candidate(candidateCode = "C1", company = "zsoft-consulting"))
        }
    }


    @Test
    fun `should delete candidate on candidate deleted event`() {

        val candidateDeletedEvent = Event(
            metaDataEvent = MetaDataEvent(eventType = "full_deletion"),
            payload = CandidatePayload(candidateCode = "C1", company = "zsoft-consulting")
        )

        candidateEventConsumer.onCandidateDataDeleted(candidateDeletedEvent)

        val candidateDelete = CandidateDelete(
            candidateCode = "C1", company = "zsoft-consulting", deletionPolicy = "full_deletion")
        verify { candidateUseCases.deleteCandidateData(candidateDelete) }
    }
}