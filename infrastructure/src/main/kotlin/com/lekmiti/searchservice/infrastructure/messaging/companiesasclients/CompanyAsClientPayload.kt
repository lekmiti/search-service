package com.lekmiti.searchservice.infrastructure.messaging.companiesasclients

import com.fasterxml.jackson.annotation.JsonProperty

data class CompanyAsClientPayload(
    @JsonProperty("company")
    val company: String
)