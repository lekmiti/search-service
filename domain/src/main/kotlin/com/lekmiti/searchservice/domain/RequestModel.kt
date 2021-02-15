package com.lekmiti.searchservice.domain

import org.springframework.data.domain.Pageable


data class RequestModel(
        val term: Term,
        val pageable: Pageable = Pageable.unpaged(),
        val scope: String?,
        val company: String)