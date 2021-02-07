package com.lekmiti.searchservice.infrastructure.restapi

import com.lekmiti.searchservice.application.SearchAppService
import com.lekmiti.searchservice.domain.RequestModel
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val log = LoggerFactory.getLogger(SearchResource::class.java)
private val response = HttpResponses(log)

@RestController
@RequestMapping("/api/v1/candidates")
class SearchResource(private val searchAppService: SearchAppService) {

    @GetMapping
    fun search(
        @RequestParam("term") term: String,
        @RequestParam("type", required = false) type: String?,
        pageable: Pageable) =
        searchAppService.searchForCandidates(RequestModel(term, pageable)).let {
            if (it.items.isEmpty()) response.notFound("No items referenced by term: $term")
            else response.ok(it, "Items referenced by term '$term': ${it.items}")
        }

}

