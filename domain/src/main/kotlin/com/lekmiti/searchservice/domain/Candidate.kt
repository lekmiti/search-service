package com.lekmiti.searchservice.domain


open class Candidate(
    val candidateCode: CandidateCode,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val phoneNumber: String? = null,
    val socialNetworks: SocialNetworks? = null,
    val tags: Tags? = null,
    val country: String? = null,
    val otherAttachments: Attachments? = null,
    open val cv: Cv? = null
) : AnItem

class Cv(
    url: String,
    type: String? = "cv",
    name: String?,
    extension: String?,
    updatedAt: String?,
    updatedBy: String?
) : Attachment(url, type, name, extension, updatedAt, updatedBy)

open class Attachment(
    val url: String,
    val type: String? = null,
    val name: String? = null,
    val extension: String? = null,
    val updatedAt: String? = null,
    val updatedBy: String? = null
)


