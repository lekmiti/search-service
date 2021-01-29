package com.lekmiti.searchservice.infrastructure.messaging.candidates

import com.lekmiti.searchservice.domain.*


class CandidatePayload(
    candidateCode: CandidateCode,
    firstName: String?,
    lastName: String?,
    email: String?,
    phoneNumber: String?,
    socialNetworks: SocialNetworks?,
    tags: Tags?,
    country: String?,
    otherAttachments: Attachments?,
    cv: Cv?)
    : Candidate(candidateCode, firstName, lastName, email, phoneNumber, socialNetworks, tags, country, otherAttachments, cv)