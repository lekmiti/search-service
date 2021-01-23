package com.lekmiti.searchservice.infrastructure.restapi

import com.lekmiti.searchservice.application.SearchAppService
import com.lekmiti.searchservice.domain.RequestModel
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/candidates")
class CandidateResource(private val searchAppService: SearchAppService) {

    @GetMapping
    fun search(
            @RequestParam("term") term: String,
            @RequestParam("type", required = false) type: String?,
            pageable: Pageable) =
            searchAppService.search(RequestModel(term, pageable)).let {
                if (it.items.isEmpty()) ResponseEntity.notFound().build()
                else ResponseEntity.ok(it)
            }

}

