package com.lekmiti.searchservice.usecases

import com.lekmiti.searchservice.domain.ResponseModel
import com.lekmiti.searchservice.domain.SearchRequestModel
import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.search.SearchScope
import com.lekmiti.searchservice.domain.search.SearchService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SearchUseCasesTest {
    private val searchService = mockk<SearchService>()
    private val searchScope = SearchScope()
    private val searchUseCases = SearchUseCases(searchService, searchScope)

    @Test
    fun `should search candidates whose lastName or emails reference the term 'lekmiti'`(){
        val requestModel = SearchRequestModel(
            term = "lekmiti",
            type = "candidate",
            client = "zsoft-consulting",
            scopes = listOf("lastName","emails"))
        every { searchService.searchForCandidates(requestModel, requestModel.scopes) } returns ResponseModel()

        val result = searchUseCases.search(requestModel)

        verify { searchService.searchForCandidates(requestModel, requestModel.scopes) }
        assertThat(result).isEqualTo(ResponseModel<Candidate>())
    }

    @Test
    fun `should search for candidates referencing the term 'lekmiti' in any field when the scope is empty`(){
        val requestModel = SearchRequestModel(
            term = "lekmiti",
            type = "candidate",
            client = "zsoft-consulting",
            scopes = emptyList())
        every { searchService.searchForCandidates(requestModel, any()) } returns ResponseModel()

        val result = searchUseCases.search(requestModel)

        // then
        val expectedScope = listOf(
            "candidateCode" ,
            "company",
            "firstName",
            "lastName ",
            "country ",
            "source ",
            "address ",
            "applicationType",
            "phoneNumbers",
            "emails",
            "tags",
            "cvList.name",
            "cvList.type",
            "cvList.tags",
            "otherAttachments.name",
            "otherAttachments.type",
            "otherAttachments.tags",
            "socialNetworks.link",
            "socialNetworks.type")
        verify { searchService.searchForCandidates(requestModel, expectedScope) }
        assertThat(result).isEqualTo(ResponseModel<Candidate>())
    }
}