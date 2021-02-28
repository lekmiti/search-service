package com.lekmiti.searchservice.infrastructure.restapi

import com.lekmiti.searchservice.domain.search.ResponseModel
import com.lekmiti.searchservice.infrastructure.CandidateForTest.aCandidateItem
import com.lekmiti.searchservice.usecases.SearchUseCases
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Pageable.unpaged
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

internal class SearchResourceTest {

    private val searchUseCases = mockk<SearchUseCases>()

    private val searchResource = SearchResource(searchUseCases)

    @Test
    fun `should return ok when an item is found`() {
        val item = aCandidateItem("C1")
        every { searchUseCases.search(any()) } returns ResponseModel(items = listOf(item))

        val response = searchResource.search("java", "candidate", emptyList(), unpaged())

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `should return not found when no item is referenced by the given term`() {
        val term = "java"
        every { searchUseCases.search(any()) } returns ResponseModel(items = emptyList())

        val exception = assertThrows(ResponseStatusException::class.java) {
            searchResource.search(term, "candidate", emptyList(), unpaged())
        }

        assertThat(exception.status).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(exception.reason).isEqualTo("No items referenced by term: '$term' found")

    }

}