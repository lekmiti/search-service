package com.lekmiti.searchservice.domain

import com.lekmiti.searchservice.domain.Attachment.Companion.mergeAttachments
import com.lekmiti.searchservice.domain.SocialNetwork.Companion.mergeSocialNetworks

open class Candidate(
    val candidateCode: CandidateCode,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: Emails = emptyList(),
    val phoneNumber: Collection<String> = emptyList(),
    val socialNetworks: SocialNetworks = emptyList(),
    val tags: Tags = emptyList(),
    val country: String? = null,
    val otherAttachments: Attachments,
    open val cvList: CVs
) : AnItem {

    fun overrideBy(other: Candidate?) =
        other
            ?.let {
                Candidate(
                    candidateCode = it.candidateCode,
                    firstName = it.firstName ?: this.firstName,
                    lastName = it.lastName ?: this.lastName,
                    country = it.country ?: this.country,
                    email = (it.email + this.email).toSet(),
                    phoneNumber = (it.phoneNumber + this.phoneNumber).toSet(),
                    tags = it.tags + this.tags,
                    cvList = mergeAttachments(this.cvList, it.cvList),
                    otherAttachments = mergeAttachments(this.otherAttachments, it.otherAttachments),
                    socialNetworks = mergeSocialNetworks(this.socialNetworks, it.socialNetworks)
                )
            } ?: this
}




