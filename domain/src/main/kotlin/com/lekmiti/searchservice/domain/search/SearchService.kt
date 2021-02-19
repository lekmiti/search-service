package com.lekmiti.searchservice.domain.search

import com.lekmiti.searchservice.domain.candidate.Candidate

interface SearchService {

    fun searchForCandidates(searchRequestModel: SearchRequestModel, scopes: Collection<String>): ResponseModel<Candidate>
}