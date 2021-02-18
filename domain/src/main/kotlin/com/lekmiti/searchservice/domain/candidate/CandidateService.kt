package com.lekmiti.searchservice.domain.candidate

import com.lekmiti.searchservice.domain.CandidateCode


interface CandidateService {

    fun saveCandidate(candidate: Candidate, index: String): Candidate

    fun updateCandidate(candidate: Candidate, index: String): Candidate

    fun findCandidateByCode(candidateCode: CandidateCode, index: String) : Candidate?

    fun deleteCandidate(candidateCode: CandidateCode, index: String)
}