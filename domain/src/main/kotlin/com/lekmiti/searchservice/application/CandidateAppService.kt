package com.lekmiti.searchservice.application

import com.lekmiti.searchservice.domain.Candidate
import com.lekmiti.searchservice.domain.CandidateDelete
import com.lekmiti.searchservice.domain.CandidateService
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger(CandidateAppService::class.java)


class CandidateAppService(private val candidateService: CandidateService) {

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