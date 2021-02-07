package com.lekmiti.searchservice.domain

import java.time.LocalDateTime

class Attachment(
    val url: String,
    val type: String? = null,
    val name: String? = null,
    val extension: String? = null,
    val updatedAt: LocalDateTime? = null,
    val updatedBy: String? = null,
    val tags: Tags = emptyList()
) {

    fun overrideBy(other: Attachment?) =
        other
            ?.let {
                Attachment(
                    url = it.url,
                    name = it.name ?: this.name,
                    type = it.type ?: this.type,
                    extension = it.extension ?: this.extension,
                    updatedAt = it.updatedAt ?: this.updatedAt,
                    updatedBy = it.updatedBy ?: this.updatedBy,
                    tags = (it.tags + this.tags).toSet()
                )
            } ?: this

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Attachment) return false
        return url == other.url
    }

    override fun hashCode() = url.hashCode()

    companion object {

        internal fun mergeAttachments(oldAttachments: Attachments, newAttachments: Attachments): Attachments {
            val mergedAttachments = oldAttachments
                .map { attachment -> attachment.overrideBy(newAttachments.find { it == attachment }) }
            val complementOfNewAttachments = newAttachments - mergedAttachments
            val complementOfOldAttachments = oldAttachments - mergedAttachments
            return mergedAttachments + complementOfNewAttachments + complementOfOldAttachments
        }
    }
}