package com.lekmiti.searchservice.infrastructure.search

import com.lekmiti.searchservice.domain.Phrase
import com.lekmiti.searchservice.infrastructure.mapping.EsCandidate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface CandidateRepository : ElasticsearchRepository<EsCandidate, String> {


    @Query("""
        {"match": {"cv.content": "?0"}}
    """)
    fun searchInCvs(phrase: Phrase, pageable: Pageable): Page<EsCandidate>?
}