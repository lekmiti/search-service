package com.lekmiti.searchservice.domain.compnayasclient

import com.lekmiti.searchservice.domain.Company

interface CompanyAsClientService {
    fun initializeNewCompanyAsClient(company: Company): Boolean
}