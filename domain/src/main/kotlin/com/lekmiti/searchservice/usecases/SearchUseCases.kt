package com.lekmiti.searchservice.usecases

import com.lekmiti.searchservice.domain.candidate.CandidateService
import com.lekmiti.searchservice.domain.RequestModel

class SearchUseCases(private val candidateService: CandidateService) {

    fun searchForCandidates(requestModel: RequestModel) =
        candidateService.searchForCandidates(requestModel)
}