package com.lekmiti.searchservice.domain


typealias Attachments= Collection<Attachment>
typealias CVs = Collection<Attachment>

class Attachment(
    val url: String,
    val name: String,
    val type: String? = null,
    val tags: Tags? = emptyList()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Attachment) return false
        return url == other.url
    }

    override fun hashCode() = url.hashCode()
}