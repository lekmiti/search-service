package com.lekmiti.searchservice.domain


interface SearchService {

    fun searchForCandidates(requestModel: RequestModel): ResponseModel<Candidate>
}