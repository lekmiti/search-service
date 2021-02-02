package com.lekmiti.searchservice.application

import com.lekmiti.searchservice.domain.CandidateService
import com.lekmiti.searchservice.domain.RequestModel

class SearchAppService(private val candidateService: CandidateService) {

    fun searchForCandidates(requestModel: RequestModel) =
        candidateService.searchForCandidates(requestModel)
}