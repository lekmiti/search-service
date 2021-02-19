package com.lekmiti.searchservice.infrastructure.config

import com.lekmiti.searchservice.domain.candidate.CandidateService
import com.lekmiti.searchservice.domain.compnayasclient.CompanyAsClientService
import com.lekmiti.searchservice.domain.search.SearchScope
import com.lekmiti.searchservice.domain.search.SearchService
import com.lekmiti.searchservice.usecases.CandidateUseCases
import com.lekmiti.searchservice.usecases.CompanyAsClientUseCases
import com.lekmiti.searchservice.usecases.SearchUseCases
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfig {

    @Bean
    fun searchScope() = SearchScope()


    @Bean
    fun searchAppService(searchService: SearchService, searchScope: SearchScope) =
        SearchUseCases(searchService, searchScope)

    @Bean
    fun candidateAppService(candidateService: CandidateService) =
        CandidateUseCases(candidateService)

    @Bean
    fun companyAsClientUseCases(companyAsClientService: CompanyAsClientService) =
        CompanyAsClientUseCases(companyAsClientService)

}