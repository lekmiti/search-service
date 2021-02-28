package com.lekmiti.searchservice.domain.candidate

import com.lekmiti.searchservice.domain.*
import com.lekmiti.searchservice.domain.search.AnItem

data class Candidate(
    val candidateCode: CandidateCode,
    val company: Company,
    val firstName: String? = null,
    val lastName: String? = null,
    val emails: Emails = emptyList(),
    val phoneNumbers: Collection<String> = emptyList(),
    val socialNetworks: SocialNetworks = emptyList(),
    val tags: Tags = emptyList(),
    val country: String? = null,
    val address: String? = null,
    val applicationType: String? = null,
    val source: String? = null,
    val otherAttachments: Attachments = emptyList(),
    val cvList: CVs = emptyList()
) : AnItem {

    fun overrideBy(other: Candidate?) =
        other
            ?.let {
                Candidate(
                    candidateCode = it.candidateCode,
                    company = it.company,
                    firstName = it.firstName ?: this.firstName,
                    lastName = it.lastName ?: this.lastName,
                    country = it.country ?: this.country,
                    source = it.source ?: this.source,
                    address = it.address ?: this.address,
                    applicationType = it.applicationType ?: this.applicationType,
                    phoneNumbers = if (it.phoneNumbers.isNullOrEmpty()) this.phoneNumbers else it.phoneNumbers,
                    emails = if (it.emails.isNullOrEmpty()) this.emails else it.emails,
                    tags = if (it.tags.isNullOrEmpty()) this.tags else it.tags,
                    cvList = if (it.cvList.isNullOrEmpty()) this.cvList else it.cvList,
                    otherAttachments = if (it.otherAttachments.isNullOrEmpty()) this.otherAttachments else it.otherAttachments,
                    socialNetworks = if (it.socialNetworks.isNullOrEmpty()) this.socialNetworks else it.socialNetworks
                )
            } ?: this

    fun delete(candidateDataToBeDeleted: CandidateDataToBeDeleted) =
        Candidate(
            candidateCode = candidateCode,
            company = company,
            firstName = firstName,
            lastName = lastName,
            country = country,
            source = source,
            address = address,
            applicationType = applicationType,
            phoneNumbers = phoneNumbers - candidateDataToBeDeleted.phoneNumbers,
            emails = emails - candidateDataToBeDeleted.emails,
            tags = tags - candidateDataToBeDeleted.tags,
            cvList = cvList - candidateDataToBeDeleted.cvList,
            otherAttachments = otherAttachments - candidateDataToBeDeleted.otherAttachments,
            socialNetworks = socialNetworks - candidateDataToBeDeleted.socialNetworks
        )

}


data class SocialNetwork(val type: String, val link: String)

data class CandidateDelete(
    val candidateCode: CandidateCode,
    val company: Company,
    val deletionPolicy: String?,
    val candidateDataToBeDeleted: CandidateDataToBeDeleted = CandidateDataToBeDeleted()
)


data class CandidateDataToBeDeleted(
    val emails: Emails = emptyList(),
    val phoneNumbers: Collection<String> = emptyList(),
    val socialNetworks: SocialNetworks = emptyList(),
    val tags: Tags = emptyList(),
    val otherAttachments: Attachments = emptyList(),
    val cvList: CVs = emptyList())




