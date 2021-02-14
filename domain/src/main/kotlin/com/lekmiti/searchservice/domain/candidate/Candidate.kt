package com.lekmiti.searchservice.domain.candidate

import com.lekmiti.searchservice.domain.*

open class Candidate(
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
    val applicationType: String? = null, // how the candidate has been found : application, sourcing, referral
    val source: String? = null,  // linkedin, sofiane , monster
    val otherAttachments: Attachments = emptyList(),
    open val cvList: CVs = emptyList()
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Candidate) return false

        if (candidateCode != other.candidateCode) return false
        if (company != other.company) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (emails != other.emails) return false
        if (phoneNumbers != other.phoneNumbers) return false
        if (socialNetworks != other.socialNetworks) return false
        if (tags != other.tags) return false
        if (country != other.country) return false
        if (address != other.address) return false
        if (applicationType != other.applicationType) return false
        if (source != other.source) return false
        if (otherAttachments != other.otherAttachments) return false
        if (cvList != other.cvList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = candidateCode.hashCode()
        result = 31 * result + company.hashCode()
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + emails.hashCode()
        result = 31 * result + phoneNumbers.hashCode()
        result = 31 * result + socialNetworks.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + (country?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (applicationType?.hashCode() ?: 0)
        result = 31 * result + (source?.hashCode() ?: 0)
        result = 31 * result + otherAttachments.hashCode()
        result = 31 * result + cvList.hashCode()
        return result
    }


}


data class SocialNetwork(val type: String, val link: String)

data class CandidateDelete(
    val candidateCode: CandidateCode,
    val company: Company,
    val deletionPolicy: String,
    val candidateDataToBeDeleted: CandidateDataToBeDeleted = CandidateDataToBeDeleted()
)


data class CandidateDataToBeDeleted(
    val emails: Emails = emptyList(),
    val phoneNumbers: Collection<String> = emptyList(),
    val socialNetworks: SocialNetworks = emptyList(),
    val tags: Tags = emptyList(),
    val otherAttachments: Attachments = emptyList(),
    val cvList: CVs = emptyList())




