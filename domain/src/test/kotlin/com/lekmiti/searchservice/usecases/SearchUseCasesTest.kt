package com.lekmiti.searchservice.usecases

import com.lekmiti.searchservice.domain.RequestModel
import com.lekmiti.searchservice.domain.ResponseModel
import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.candidate.CandidateService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SearchUseCasesTest {
    private val candidateService = mockk<CandidateService>()
    private val searchUseCases = SearchUseCases(candidateService)

    @Test
    fun `should search candidates referenced by the word Java`(){
        val requestModel = RequestModel(
            term = "Java",
            scope = "candidate",
            company = "zsoft-consulting")
        every { candidateService.searchForCandidates(requestModel) } returns ResponseModel()

        val result = searchUseCases.searchForCandidates(requestModel)

        verify { candidateService.searchForCandidates(requestModel) }
        assertThat(result).isEqualTo(ResponseModel<Candidate>())

    }
}