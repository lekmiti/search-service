package com.lekmiti.searchservice.domain


interface CandidateService {

    fun searchForCandidates(requestModel: RequestModel): ResponseModel<Candidate>

    fun saveCandidate(candidate: Candidate): Candidate

    fun updateCandidate(candidate: Candidate): Candidate

    fun findCandidateByCode(candidateCode: CandidateCode) : Candidate?

    fun deleteCandidate(candidateCode: CandidateCode)
}