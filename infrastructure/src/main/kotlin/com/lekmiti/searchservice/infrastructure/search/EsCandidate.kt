package com.lekmiti.searchservice.infrastructure.search

import com.lekmiti.searchservice.domain.*
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "candidates")
class EsCandidate(
        candidateCode: CandidateCode,
        firstName: String?,
        lastName: String?,
        email: Emails,
        phoneNumber: Collection<String>,
        socialNetworks: SocialNetworks,
        tags: Tags,
        country: String?,
        otherAttachments: Attachments,
        @Field(type = FieldType.Nested, includeInParent = true)
        override val cvList: CVs,
        @Id val id: String? = null)
    : Candidate(candidateCode, firstName, lastName, email, phoneNumber, socialNetworks, tags, country, otherAttachments, cvList)