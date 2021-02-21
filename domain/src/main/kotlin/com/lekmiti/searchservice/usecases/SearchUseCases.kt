package com.lekmiti.searchservice.usecases

import com.lekmiti.searchservice.domain.search.SearchRequestModel
import com.lekmiti.searchservice.domain.search.SearchScope
import com.lekmiti.searchservice.domain.search.SearchService

class SearchUseCases(
    private val searchService: SearchService,
    private val scope: SearchScope) {

    fun search(searchRequestModel: SearchRequestModel) =
        searchService.searchForCandidates(
            searchRequestModel.lowerCaseClient(),
            scope.getSearchScopesForCandidates(searchRequestModel.scopes))

}