package com.lekmiti.searchservice.infrastructure.restapi

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity


class HttpResponses {
    fun <T> ok(body: T) = ResponseEntity.ok(body)

    fun <T> notFound(body: T, message: () -> String) = ResponseEntity.status(NOT_FOUND).body(message)
}
