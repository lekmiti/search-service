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
    val firstName: String?,
    @JsonProperty("lastName")
    val lastName: String?,
    @JsonProperty("emails")
    val emails: Emails?,
    @JsonProperty("phoneNumbers")
    val phoneNumbers: Collection<String>?,
    @JsonProperty("socialNetworks")
    val socialNetworks: SocialNetworks?,
    @JsonProperty("tags")
    val tags: Tags?,
    @JsonProperty("country")
    val country: String?,
    @JsonProperty("address")
    val address: String?,
    @JsonProperty("applicationType")
    val applicationType: String?,
    @JsonProperty("source")
    val source: String?,
    @JsonProperty("otherAttachments")
    val otherAttachments: Attachments?,
    @JsonProperty("cvList")
    val cvList: CVs?
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

    fun toCandidateDelete(deletionPolicy: String) =
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


