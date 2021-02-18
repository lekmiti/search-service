package com.lekmiti.searchservice.domain

import org.springframework.data.domain.Pageable


data class SearchRequestModel(
    val term: Term,
    val pageable: Pageable = Pageable.unpaged(),
    val type: String?,
    val client: String,
    val scopes: Collection<String> = emptyList()
)