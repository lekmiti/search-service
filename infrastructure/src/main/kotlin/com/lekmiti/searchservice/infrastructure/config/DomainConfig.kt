package com.lekmiti.searchservice.infrastructure.config

import com.lekmiti.searchservice.usecases.CandidateUseCases
import com.lekmiti.searchservice.usecases.SearchUseCases
import com.lekmiti.searchservice.domain.candidate.CandidateService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfig {

    @Bean
    fun searchAppService(candidateService: CandidateService) = SearchUseCases(candidateService)

    @Bean
    fun candidateAppService(candidateService: CandidateService) = CandidateUseCases(candidateService)


}