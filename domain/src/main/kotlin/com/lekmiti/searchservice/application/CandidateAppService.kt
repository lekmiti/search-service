package com.lekmiti.searchservice.application

import com.lekmiti.searchservice.domain.Candidate
import com.lekmiti.searchservice.domain.CandidateService

class CandidateAppService(private val candidateService: CandidateService) {

    fun saveOrUpdateCandidate(candidate: Candidate) =
        candidateService.findCandidateByCode(candidate.candidateCode)
            ?.let { candidateService.updateCandidate(it.overrideBy(candidate)) }
            ?: candidateService.saveCandidate(candidate)
}