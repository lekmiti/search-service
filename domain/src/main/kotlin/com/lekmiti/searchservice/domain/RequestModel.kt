package com.lekmiti.searchservice.domain

import org.springframework.data.domain.Pageable


data class RequestModel(
        val term: Term,
        val pageable: Pageable,
        val type: String? = null)