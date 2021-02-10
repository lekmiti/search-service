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
        emails: Emails,
        phoneNumbers: Collection<String>,
        socialNetworks: SocialNetworks,
        tags: Tags,
        country: String?,
        address: String?,
        applicationType: String?,
        source: String?,
        otherAttachments: Attachments,
        @Field(type = FieldType.Nested, includeInParent = true)
        override val cvList: CVs,
        @Id val id: String? = null
) : Candidate(
        candidateCode,
        firstName,
        lastName,
        emails,
        phoneNumbers,
        socialNetworks,
        tags,
        country,
        address,
        applicationType,
        source,
        otherAttachments,
        cvList
)