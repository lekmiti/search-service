package com.lekmiti.searchservice.infrastructure.restapi

import com.lekmiti.searchservice.domain.Phrase
import com.lekmiti.searchservice.infrastructure.mapping.EsCandidate
import com.lekmiti.searchservice.infrastructure.search.CandidateRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/candidates")
data class CandidateResource(
        private val candidateRepository: CandidateRepository,
        private val elasticsearchRestTemplate: ElasticsearchRestTemplate) {


    @GetMapping
    fun search(
            @RequestParam("phrase") phrase: Phrase,
            pageable: Pageable): ResponseEntity<Page<EsCandidate>> =
            candidateRepository.searchInCvs(phrase, pageable)?.let {
                when {
                    it.isEmpty -> ResponseEntity.notFound().build()
                    else -> ResponseEntity.ok(it)
                }
            } ?: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
}

