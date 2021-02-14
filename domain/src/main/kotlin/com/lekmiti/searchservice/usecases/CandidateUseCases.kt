package com.lekmiti.searchservice.usecases

import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.candidate.CandidateDelete
import com.lekmiti.searchservice.domain.candidate.CandidateService
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger(CandidateUseCases::class.java)


class CandidateUseCases(private val candidateService: CandidateService) {

    fun createOrUpdateCandidate(candidate: Candidate) =
        candidateService.findCandidateByCode(candidate.candidateCode, candidate.company)
            ?.let { candidateService.updateCandidate(it.overrideBy(candidate), candidate.company) }
            ?: candidateService.saveCandidate(candidate, candidate.company)

    fun deleteCandidateData(candidate: CandidateDelete) =
        candidateService.findCandidateByCode(candidate.candidateCode, candidate.company)
            ?.let {
                when (candidate.deletionPolicy) {
                    "full_deletion" -> candidateService.deleteCandidate(candidate.candidateCode, candidate.company)
                    "partial_deletion" -> candidateService.updateCandidate(it.delete(candidate.candidateDataToBeDeleted), candidate.company)
                    else -> throw IllegalArgumentException("No such deletion policy as '${candidate.deletionPolicy}'")
                }
            } ?: log.warn("Unable to apply deletion operation: candidate ${candidate.candidateCode} not found!")

}