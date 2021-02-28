package com.lekmiti.searchservice.infrastructure.messaging.candidates

import com.fasterxml.jackson.annotation.JsonProperty
import com.lekmiti.searchservice.domain.*
import com.lekmiti.searchservice.domain.candidate.*

data class CandidatePayload(
    @JsonProperty("candidateCode")
    val candidateCode: CandidateCode,
    @JsonProperty("company")
    val company: Company,
    @JsonProperty("firstName")
    val firstName: String? = null,
    @JsonProperty("lastName")
    val lastName: String? = null,
    @JsonProperty("emails")
    val emails: Emails? = emptyList(),
    @JsonProperty("phoneNumbers")
    val phoneNumbers: Collection<String>? = emptyList(),
    @JsonProperty("socialNetworks")
    val socialNetworks: SocialNetworks? = emptyList(),
    @JsonProperty("tags")
    val tags: Tags? = emptyList(),
    @JsonProperty("country")
    val country: String? = null,
    @JsonProperty("address")
    val address: String? = null,
    @JsonProperty("applicationType")
    val applicationType: String? = null,
    @JsonProperty("source")
    val source: String? = null,
    @JsonProperty("otherAttachments")
    val otherAttachments: Attachments? = emptyList(),
    @JsonProperty("cvList")
    val cvList: CVs? = emptyList()
) {
    fun toCandidateUpsert() =
        Candidate(
            candidateCode = candidateCode,
            company = company,
            firstName = firstName,
            lastName = lastName,
            emails = emails.orEmpty(),
            phoneNumbers = phoneNumbers.orEmpty(),
            tags = tags.orEmpty(),
            country = country,
            address = address,
            socialNetworks = socialNetworks.orEmpty(),
            applicationType = applicationType,
            source = source,
            otherAttachments = otherAttachments.orEmpty(),
            cvList = cvList.orEmpty()
        )

    fun toCandidateDelete(deletionPolicy: String?) =
        CandidateDelete(
            candidateCode = candidateCode,
            company = company,
            deletionPolicy = deletionPolicy,
            candidateDataToBeDeleted = CandidateDataToBeDeleted(
                emails = emails.orEmpty(),
                phoneNumbers = phoneNumbers.orEmpty(),
                tags = tags.orEmpty(),
                socialNetworks = socialNetworks.orEmpty(),
                otherAttachments = otherAttachments.orEmpty()
            )

        )
}


