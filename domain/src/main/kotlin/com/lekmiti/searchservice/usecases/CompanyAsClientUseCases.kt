package com.lekmiti.searchservice.usecases

import com.lekmiti.searchservice.domain.Company
import com.lekmiti.searchservice.domain.compnayasclient.CompanyAsClientService
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger(CompanyAsClientUseCases::class.java)

class CompanyAsClientUseCases(private val companyAsClientService : CompanyAsClientService) {

    fun initializeNewCompanyAsClient(company: Company) =
        when (companyAsClientService.initializeNewCompanyAsClient(company.toLowerCase())) {
            true -> log.info("New company '${company.toLowerCase()}' has been successfully initialized as client")
            false -> log.error("'${company.toLowerCase()}' company initialization failed")
        }

}