package com.lekmiti.searchservice.infrastructure.messaging.candidates

import com.lekmiti.searchservice.domain.*


class CandidatePayload(
        candidateCode: CandidateCode,
        firstName: String?,
        lastName: String?,
        email: Emails?,
        phoneNumber: Collection<String>?,
        socialNetworks: SocialNetworks?,
        tags: Tags?,
        country: String?,
        otherAttachments: Attachments? = emptyList(),
        cvList: CVs? = emptyList()
) : Candidate(
        candidateCode,
        firstName,
        lastName,
        email.orEmpty(),
        phoneNumber.orEmpty(),
        socialNetworks.orEmpty(),
        tags.orEmpty(),
        country,
        otherAttachments.orEmpty(),
        cvList.orEmpty())