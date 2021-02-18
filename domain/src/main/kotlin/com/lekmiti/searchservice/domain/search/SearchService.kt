package com.lekmiti.searchservice.domain.search

import com.lekmiti.searchservice.domain.ResponseModel
import com.lekmiti.searchservice.domain.SearchRequestModel
import com.lekmiti.searchservice.domain.candidate.Candidate

interface SearchService {

    fun searchForCandidates(searchRequestModel: SearchRequestModel, scopes: Collection<String>): ResponseModel<Candidate>

}