package com.lekmiti.searchservice.domain


interface CandidateService {
    fun searchForCandidates(requestModel: RequestModel): ResponseModel<Candidate>
}