package com.lekmiti.searchservice.usecases

import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.candidate.CandidateDataToBeDeleted
import com.lekmiti.searchservice.domain.candidate.CandidateDelete
import com.lekmiti.searchservice.domain.candidate.CandidateService
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class CandidateUseCasesTest {

    private val candidateService = mockk<CandidateService>()
    private val candidateUseCase = CandidateUseCases(candidateService)

    @Test
    fun `should create new candidate`() {
        // given
        val candidateCode = "C1"
        val company = "zsoft-consulting"
        val candidate = Candidate(candidateCode, company)
        every { candidateService.findCandidateByCode(candidateCode, company) } returns null
        every { candidateService.saveCandidate(candidate, company) } returns candidate

        // when
        val result = candidateUseCase.createOrUpdateCandidate(candidate)

        verify { candidateService.saveCandidate(candidate, candidate.company) }
        verify(exactly = 0) { candidateService.updateCandidate(any(), any()) }
        assertThat(result).isEqualTo(candidate)
    }

    @Test
    fun `should update existing candidate`() {
        // given
        val candidateCode = "C1"
        val company = "zsoft-consulting"
        val oldCandidate = Candidate(
            candidateCode = candidateCode,
            company = company,
            firstName = "mh",
            emails = listOf("elhadi.lekmiti@zsoft-consulting.com"),
            tags = listOf("developer")
        )
        val newCandidate = Candidate(
            candidateCode = candidateCode,
            company = company,
            lastName = "lek",
            tags = listOf("developer", "java", "kotlin")
        )
        val expectedCandidate = Candidate(
            candidateCode = candidateCode,
            company = company,
            firstName = "mh", // from old
            emails = listOf("elhadi.lekmiti@zsoft-consulting.com"),// from old
            lastName = "lek", // from new
            tags = listOf("developer", "java", "kotlin") // from new
        )
        every { candidateService.findCandidateByCode(candidateCode, company) } returns oldCandidate
        every { candidateService.updateCandidate(expectedCandidate, company) } returns expectedCandidate

        // when
        val result = candidateUseCase.createOrUpdateCandidate(newCandidate)

        // then

        verify { candidateService.updateCandidate(eq(expectedCandidate), oldCandidate.company) }
        verify(exactly = 0) { candidateService.saveCandidate(any(), any()) }
        assertThat(result).isEqualTo(expectedCandidate)
    }

    @Test
    fun `should delete candidate radically`() {
        // given
        val candidateCode = "C1"
        val company = "zsoft-consulting"
        val candidate = Candidate(candidateCode, company)
        every { candidateService.findCandidateByCode(candidateCode, company) } returns candidate
        justRun { candidateService.deleteCandidate(candidateCode, company) }

        // when
        val candidateDelete = CandidateDelete(candidateCode, company, "full_deletion")
        candidateUseCase.deleteCandidateData(candidateDelete)

        verify { candidateService.deleteCandidate(candidateDelete.candidateCode, candidateDelete.company) }
        verify(exactly = 0) { candidateService.updateCandidate(any(), any()) }

    }

    @Test
    fun `should delete some candidate data`() {
        // given
        val candidateCode = "C1"
        val company = "zsoft-consulting"
        val oldCandidate = Candidate(
            candidateCode = candidateCode,
            company = company,
            tags = listOf("developer", "java", "kotlin")
        )
        val expectedCandidate = Candidate(
            candidateCode = candidateCode,
            company = company,
            tags = listOf("java")
        )
        every { candidateService.findCandidateByCode(candidateCode, company) } returns oldCandidate
        every { candidateService.updateCandidate(eq(expectedCandidate), company) } returns expectedCandidate

        // when
        val candidateDelete = CandidateDelete(
            candidateCode = candidateCode,
            company = company,
            deletionPolicy = "partial_deletion",
            candidateDataToBeDeleted = CandidateDataToBeDeleted(tags = listOf("kotlin", "developer"))
        )
        candidateUseCase.deleteCandidateData(candidateDelete)

        // then
        verify { candidateService.updateCandidate(eq(expectedCandidate), candidateDelete.company) }
        verify(exactly = 0) { candidateService.deleteCandidate(any(), any()) }

    }

    @Test
    fun `should throw an exception when the deletion policy is wrong`() {
        // given
        val candidateCode = "C1"
        val company = "zsoft-consulting"
        val candidate = Candidate(candidateCode, company = company)
        every { candidateService.findCandidateByCode(candidateCode, company) } returns candidate

        // when - then
        val candidateDelete = CandidateDelete(candidateCode, company, "unknown_policy")
        assertThatThrownBy { candidateUseCase.deleteCandidateData(candidateDelete) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("No such deletion policy as 'unknown_policy'")

    }

}