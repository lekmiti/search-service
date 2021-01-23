package com.lekmiti.searchservice.infrastructure.config

import com.lekmiti.searchservice.application.SearchAppService
import com.lekmiti.searchservice.domain.SearchService
import com.lekmiti.searchservice.infrastructure.search.ElasticsSearchService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate

@Configuration
class DomainConfig() {

    @Bean
    fun searchAppService(searchService: SearchService) = SearchAppService(searchService)

    @Bean
    fun searchService(elasticsearchRestTemplate: ElasticsearchRestTemplate) = ElasticsSearchService(elasticsearchRestTemplate)
}