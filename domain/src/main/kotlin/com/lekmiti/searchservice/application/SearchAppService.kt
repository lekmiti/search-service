package com.lekmiti.searchservice.application

import com.lekmiti.searchservice.domain.Phrase
import com.lekmiti.searchservice.domain.SearchService


class SearchAppService(private val searchService: SearchService) {

    fun searchCandidates(phrase: Phrase) =
            searchService.searchCandidateByPhrase(phrase)

}


