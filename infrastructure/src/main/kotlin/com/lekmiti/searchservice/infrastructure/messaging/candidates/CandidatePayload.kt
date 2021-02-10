package com.lekmiti.searchservice.infrastructure.messaging.candidates

import com.lekmiti.searchservice.domain.*


class CandidatePayload(
        candidateCode: CandidateCode,
        firstName: String?,
        lastName: String?,
        emails: Emails?,
        phoneNumbers: Collection<String>?,
        socialNetworks: SocialNetworks?,
        tags: Tags?,
        country: String?,
        address: String?,
        applicationType: String?,
        source: String?,
        otherAttachments: Attachments?,
        cvList: CVs?
) : Candidate(
        candidateCode,
        firstName,
        lastName,
        emails.orEmpty(),
        phoneNumbers.orEmpty(),
        socialNetworks.orEmpty(),
        tags.orEmpty(),
        country,
        address,
        applicationType,
        source,
        otherAttachments.orEmpty(),
        cvList.orEmpty()
)