package com.lekmiti.searchservice.infrastructure.messaging.candidates

import com.lekmiti.searchservice.domain.candidate.Attachment
import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.candidate.CandidateDataToBeDeleted
import com.lekmiti.searchservice.domain.candidate.CandidateDelete
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CandidatePayloadTest {

    @Test
    fun `should map payload to candidate delete`() {
        val candidatePayload = CandidatePayload(
            candidateCode = "C1",
            company = "zsoft-consulting",
            emails = listOf("lekmiti1992@gmail.com", "elhadi.lekmiti@zsoft-consulting.com"),
            phoneNumbers = listOf("0753571104"),
            tags = listOf("java", "devolpper"),
            socialNetworks = emptyList(),
            cvList = listOf(
                Attachment(
                        name =  "mohamed-elhadi.lekmiti.pdf",
                        url = "/../AWS/S3/mohamed-elhadi.lekmiti.pdf",
                        content = "content",
                        type = "cv",
                        tags = listOf("java", "kotlin", "elasticsearch"))
            )
        )
        val result = candidatePayload.toCandidateDelete("full_deletion")

        val expected = CandidateDelete(
            candidateCode = "C1",
            company = "zsoft-consulting",
            deletionPolicy = "full_deletion",
            candidateDataToBeDeleted = CandidateDataToBeDeleted(
                emails = listOf("lekmiti1992@gmail.com", "elhadi.lekmiti@zsoft-consulting.com"),
                phoneNumbers = listOf("0753571104"),
                tags = listOf("java", "devolpper"),
                socialNetworks = emptyList(),
                cvList = listOf(
                    Attachment(
                        name =  "mohamed-elhadi.lekmiti.pdf",
                        url = "/../AWS/S3/mohamed-elhadi.lekmiti.pdf",
                        content = "content",
                        type = "cv",
                        tags = listOf("java", "kotlin", "elasticsearch"))
                )
            )
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should map payload to candidate`() {
        val candidatePayload = CandidatePayload(
            candidateCode = "C1",
            company = "zsoft-consulting",
            emails = listOf("lekmiti1992@gmail.com", "elhadi.lekmiti@zsoft-consulting.com"),
            phoneNumbers = listOf("0753571104"),
            tags = listOf("java", "devolpper"),
            socialNetworks = emptyList(),
            cvList = listOf(
                Attachment(
                    name =  "mohamed-elhadi.lekmiti.pdf",
                    url = "/../AWS/S3/mohamed-elhadi.lekmiti.pdf",
                    content = "content",
                    type = "cv",
                    tags = listOf("java", "kotlin", "elasticsearch"))
            )
        )
        val result = candidatePayload.toCandidateUpsert()

        val candidate = Candidate(
            candidateCode = "C1",
            company = "zsoft-consulting",
            emails = listOf("lekmiti1992@gmail.com", "elhadi.lekmiti@zsoft-consulting.com"),
            phoneNumbers = listOf("0753571104"),
            tags = listOf("java", "devolpper"),
            socialNetworks = emptyList(),
            cvList = listOf(
                Attachment(
                    name =  "mohamed-elhadi.lekmiti.pdf",
                    url = "/../AWS/S3/mohamed-elhadi.lekmiti.pdf",
                    content = "content",
                    type = "cv",
                    tags = listOf("java", "kotlin", "elasticsearch"))
            )
        )
        assertThat(result).isEqualTo(candidate)
    }
}