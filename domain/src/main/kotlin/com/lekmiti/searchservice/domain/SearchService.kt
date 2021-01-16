package com.lekmiti.searchservice.domain


interface SearchService {

    fun searchCandidateByPhrase(phrase: Phrase): Candidate
}