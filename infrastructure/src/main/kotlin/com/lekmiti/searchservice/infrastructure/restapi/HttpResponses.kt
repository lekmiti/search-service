package com.lekmiti.searchservice.infrastructure.restapi

import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException


class HttpResponses(private val log: Logger) {

    fun <T> ok(body: T, logMessage: String? = null): ResponseEntity<T> {
        logMessage?.let { log.info(logMessage) }
        return ResponseEntity.ok(body)
    }

    fun <T> notFound(logMessage: String?, leak: Boolean = false): ResponseEntity<T> {
        logMessage?.let { log.warn(logMessage) }
        throwResponseStatusException(NOT_FOUND, leak, logMessage)
    }


    private
    fun throwResponseStatusException(status: HttpStatus, leak: Boolean = false, message: String?): Nothing {
        throw ResponseStatusException(status, if (leak) message else null)
    }
}
