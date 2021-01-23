package com.lekmiti.searchservice.domain


interface SearchService {

    fun search(requestModel: RequestModel): ResponseModel<Candidate>
}