package com.lekmiti.searchservice.infrastructure.config

import com.lekmiti.searchservice.application.CandidateAppService
import com.lekmiti.searchservice.application.SearchAppService
import com.lekmiti.searchservice.domain.CandidateService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfig {

    @Bean
    fun searchAppService(candidateService: CandidateService) = SearchAppService(candidateService)

    @Bean
    fun candidateAppService(candidateService: CandidateService) = CandidateAppService(candidateService)


}