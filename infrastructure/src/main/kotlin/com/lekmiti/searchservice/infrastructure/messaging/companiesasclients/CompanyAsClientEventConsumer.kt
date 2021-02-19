package com.lekmiti.searchservice.infrastructure.messaging.companiesasclients


import com.lekmiti.searchservice.infrastructure.messaging.Event
import com.lekmiti.searchservice.usecases.CompanyAsClientUseCases
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


private val log = LoggerFactory.getLogger(CompanyAsClientEventConsumer::class.java)

@Component
@Transactional
class CompanyAsClientEventConsumer(private val companyAsClientUseCases: CompanyAsClientUseCases) {

    @StreamListener("company-as-client-init")
    fun onCandidateCreated(@Payload initCompanyEvent: Event<CompanyAsClientPayload>) {
        log.info("company-as-client-init event received $initCompanyEvent")
        companyAsClientUseCases.initializeNewCompanyAsClient(initCompanyEvent.payload.company)
    }

}