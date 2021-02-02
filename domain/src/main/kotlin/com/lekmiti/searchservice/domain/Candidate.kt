package com.lekmiti.searchservice.domain


open class Candidate(
    val candidateCode: CandidateCode,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: Emails = emptyList(),
    val phoneNumber: Collection<String> = emptyList(),
    val socialNetworks: SocialNetworks = emptyList(),
    val tags: Tags = emptyList(),
    val country: String? = null,
    val otherAttachments: Attachments = emptyList(),
    open val cv: Cv? = null
) : AnItem {

    fun overrideBy(other: Candidate?) =
        other?.let {
            Candidate(
                candidateCode = it.candidateCode,
                firstName = it.firstName ?: this.firstName,
                lastName = it.lastName ?: this.lastName,
                country = it.country ?: this.country,
                email = (it.email + this.email).toSet(),
                phoneNumber = (it.phoneNumber + this.phoneNumber).toSet(),
                otherAttachments = (it.otherAttachments + this.otherAttachments).toSet(),
                tags = it.tags + this.tags,
                cv = this.cv?.overrideBy(it.cv)
            )
        } ?: this


}


class Cv(
    url: String? = null,
    name: String? = null,
    extension: String? = null,
    updatedAt: String? = null,
    updatedBy: String? = null
) : Attachment(url, "cv", name, extension, updatedAt, updatedBy) {

    fun overrideBy(other: Cv?) =
        other
            ?.let {
                Cv(
                    url = it.url ?: this.url,
                    name = it.name ?: this.name,
                    extension = it.extension ?: this.extension,
                    updatedAt = it.updatedAt ?: this.updatedAt,
                    updatedBy = it.updatedBy ?: this.updatedBy
                )
            } ?: this

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Cv) return false
        if (!super.equals(other)) return false
        return true
    }


}


open class Attachment(
    val url: String? = null,
    val type: String? = null,
    val name: String? = null,
    val extension: String? = null,
    val updatedAt: String? = null,
    val updatedBy: String? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Attachment) return false

        if (url != other.url) return false
        if (type != other.type) return false
        if (name != other.name) return false
        if (extension != other.extension) return false

        return true
    }

    override fun hashCode(): Int {
        var result = url?.hashCode() ?: 0
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (extension?.hashCode() ?: 0)
        return result
    }
}

data class SocialNetwork(val type: String, val link: String)


