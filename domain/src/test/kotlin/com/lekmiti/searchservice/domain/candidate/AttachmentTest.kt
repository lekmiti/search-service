package com.lekmiti.searchservice.domain.candidate

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AttachmentTest {

    @Test
    fun `should be equal when the urls are identical`() {
        val attachment =  Attachment(url = "url", name = "attachment1", content = "content1")
        val other =  Attachment(url = "url", name = "attachment2", content = "content2")
        assertThat(attachment == other).isTrue()
        assertThat(attachment.hashCode() == other.hashCode()).isTrue()
    }

    @Test
    fun `should not be equal when the urls are different`() {
        val attachment =  Attachment(url = "url", name = "attachment", type = "motivation letter", content = "content")
        val other =  Attachment(url = "url2", name = "attachment", type = "motivation letter",  content = "content")
        assertThat(attachment == other).isFalse()
        assertThat(attachment.hashCode() == other.hashCode()).isFalse()

    }
}