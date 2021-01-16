package com.lekmiti.searchservice.infrastructure.mapping

import com.lekmiti.searchservice.domain.CV
import com.lekmiti.searchservice.domain.Candidate
import com.lekmiti.searchservice.domain.CandidateCode
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

        @Id val id: String,
        @Field(type = FieldType.Nested, includeInParent = true)
        override val cv: CV?)

    : Candidate(condidateNbr, firstName, lastName, email, cv)