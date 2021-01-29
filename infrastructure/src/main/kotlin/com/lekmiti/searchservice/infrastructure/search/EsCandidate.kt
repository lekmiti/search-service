package com.lekmiti.searchservice.infrastructure.search

import com.fasterxml.jackson.annotation.JsonIgnore
import com.lekmiti.searchservice.domain.*
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "candidate")
class EsCandidate(
        candidateCode: CandidateCode,
        firstName: String?,
        lastName: String?,
        email: String?,
        phoneNumber: String?,
        socialNetworks: SocialNetworks?,
        tags: Tags?,
        country: String?,
        otherAttachments: Attachments?,
        @JsonIgnore
        @Id val id: String,
        @Field(type = FieldType.Nested, includeInParent = true)
        override val cv: Cv?)
    : Candidate(candidateCode, firstName, lastName, email, phoneNumber, socialNetworks, tags, country, otherAttachments, cv)