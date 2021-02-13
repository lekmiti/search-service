package com.lekmiti.searchservice.usecases

import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.candidate.CandidateDelete
import com.lekmiti.searchservice.domain.candidate.CandidateService
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger(CandidateUseCases::class.java)


class CandidateUseCases(private val candidateService: CandidateService) {

    fun saveOrUpdateCandidate(candidate: Candidate) =
        candidateService.findCandidateByCode(candidate.candidateCode)
            ?.let { candidateService.updateCandidate(it.overrideBy(candidate)) }
            ?: candidateService.saveCandidate(candidate)

    fun deleteCandidateData(candidate: CandidateDelete) =
        candidateService.findCandidateByCode(candidate.candidateCode)
            ?.let {
                when (candidate.deletionPolicy) {
                    "full_deletion" -> candidateService.deleteCandidate(candidate.candidateCode)
                    "partial_deletion" -> candidateService.updateCandidate(
                        it.deleteCandidateData(candidate.candidateDataToBeDeleted))
                    else -> throw IllegalArgumentException("No such deletion policy as '${candidate.deletionPolicy}'")
                }
            } ?: log.warn("Unable to apply deletion operation: candidate ${candidate.candidateCode} not found!")

}