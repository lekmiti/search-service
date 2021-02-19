package com.lekmiti.searchservice.usecases

import com.lekmiti.searchservice.domain.compnayasclient.CompanyAsClientService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class CompanyAsClientUseCasesTest {
    private val companyAsClientService = mockk<CompanyAsClientService>()
    private val companyAsClientUseCases = CompanyAsClientUseCases(companyAsClientService)

    @Test
    fun `should initialize a new company as client with lowercase index name `() {
        // given

        every {companyAsClientService.initializeNewCompanyAsClient("zsoft-consulting") } returns true

        companyAsClientUseCases.initializeNewCompanyAsClient("ZSOFT-CONSULTING")

        verify { companyAsClientService.initializeNewCompanyAsClient("zsoft-consulting") }
    }
}