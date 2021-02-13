package com.lekmiti.searchservice.domain.candidate

import com.lekmiti.searchservice.domain.CandidateCode
import com.lekmiti.searchservice.domain.RequestModel
import com.lekmiti.searchservice.domain.ResponseModel


interface CandidateService {

    fun searchForCandidates(requestModel: RequestModel): ResponseModel<Candidate>

    fun saveCandidate(candidate: Candidate): Candidate

    fun updateCandidate(candidate: Candidate): Candidate

    fun findCandidateByCode(candidateCode: CandidateCode) : Candidate?

    fun deleteCandidate(candidateCode: CandidateCode)
}