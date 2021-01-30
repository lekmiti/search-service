package com.lekmiti.searchservice.application

import com.lekmiti.searchservice.domain.RequestModel
import com.lekmiti.searchservice.domain.CandidateService

class SearchAppService(private val candidateService: CandidateService) {

    fun searchForCandidates(requestModel: RequestModel) = candidateService.searchForCandidates(requestModel)
}