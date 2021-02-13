package com.lekmiti.searchservice.infrastructure.persistence

import com.lekmiti.searchservice.domain.CandidateCode
import com.lekmiti.searchservice.infrastructure.search.EsCandidate
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ElasticsearchCandidateRepository : ElasticsearchRepository<EsCandidate, String> {
    fun findByCandidateCode(candidateCode: CandidateCode): EsCandidate?
    fun deleteByCandidateCode(candidateCode: CandidateCode)
}