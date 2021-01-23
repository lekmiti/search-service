package com.lekmiti.searchservice.infrastructure.search

import com.fasterxml.jackson.annotation.JsonIgnore
import com.lekmiti.searchservice.domain.Candidate
import com.lekmiti.searchservice.domain.CandidateCode
import com.lekmiti.searchservice.domain.Cv
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "candidate")
class EsCandidate(
        condidateNbr: CandidateCode,
        firstName: String?,
        lastName: String?,
        email: String?,
        @JsonIgnore
        @Id val id: String,
        @Field(type = FieldType.Nested, includeInParent = true)
        override val cv: Cv?)
    : Candidate(condidateNbr, firstName, lastName, email, cv)