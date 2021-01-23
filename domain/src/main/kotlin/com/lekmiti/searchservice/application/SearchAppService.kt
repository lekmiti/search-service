package com.lekmiti.searchservice.application

import com.lekmiti.searchservice.domain.RequestModel
import com.lekmiti.searchservice.domain.SearchService

class SearchAppService(private val searchService: SearchService) {

    fun search(requestModel: RequestModel) = searchService.search(requestModel)
}