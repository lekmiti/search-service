package com.lekmiti.searchservice.domain.candidate

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CandidateTest {

    @Test
    fun `should override candidate`() {
        // given
        val candidateCode = "C1"
        val company = "zsoft-consulting"
        val oldCandidate = Candidate(
            candidateCode = candidateCode,
            company = company,
            firstName = "mh",
            lastName = "lek",
            address = "old address",
            emails = listOf("elhadi.lekmiti@zsoft-consulting.com"),
            tags = listOf("developer"),
            cvList = listOf(Attachment(url = "old_url", name = "old_url")),
            socialNetworks = listOf(SocialNetwork(type = "linkedin", link = "linkedin_url"))
        )

        val newCandidate = Candidate(
            candidateCode = candidateCode,
            company = company,
            lastName = "lekmiti",
            address = "new address",
            phoneNumbers = listOf("0753571104"),
            tags = listOf("developer", "java", "kotlin"),
            cvList = listOf(Attachment(url = "new_url", name = "new_url"))
        )

        val expectedCandidate = Candidate(
            candidateCode = candidateCode,
            company = company,
            firstName = "mh", // from old
            emails = listOf("elhadi.lekmiti@zsoft-consulting.com"), // from old
            socialNetworks = listOf(SocialNetwork(type = "linkedin", link = "linkedin_url")), // from old
            lastName = "lekmiti", // from new
            address = "new address", // from new
            phoneNumbers = listOf("0753571104"), // from new
            tags = listOf("developer", "java", "kotlin"), // from new
            cvList = listOf(Attachment(url = "new_url", name = "new_url")) // from new

        )

        // then
        assertThat(oldCandidate.overrideBy(newCandidate)).isEqualTo(expectedCandidate)

    }

    @Test
    fun `should return this`() {
        val candidate = Candidate(
            candidateCode = "C1",
            company = "zsoft-consulting",
            firstName = "mh",
            lastName = "lek",
            address = "old address",
            emails = listOf("elhadi.lekmiti@zsoft-consulting.com"),
            tags = listOf("developer"),
            cvList = listOf(Attachment(url = "old_url", name = "old_url")),
            socialNetworks = listOf(SocialNetwork(type = "linkedin", link = "linkedin_url"))
        )
        assertThat(candidate.overrideBy(null)).isEqualTo(candidate)


    }

    @Test
    fun `should delete candidate data`() {
        // given
        val candidateCode = "C1"
        val company = "zsoft-consulting"
        val oldCandidate = Candidate(
            candidateCode = candidateCode,
            company = company,
            firstName = "mh",
            lastName = "lek",
            address = "old address",
            country = "Algeria",
            applicationType = "sourcing",
            source = "linkedIn",
            phoneNumbers = listOf("0753571104"),
            emails = listOf("elhadi.lekmiti@zsoft-consulting.com"),
            tags = listOf("developer", "java", "kotlin"),
            cvList = listOf(Attachment(url = "old_url", name = "old_url")),
            socialNetworks = listOf(SocialNetwork(type = "linkedin", link = "linkedin_url"))
        )


        val candidateDataToBeDeleted = CandidateDataToBeDeleted(
            tags = listOf("kotlin", "developer"),
            emails = listOf("elhadi.lekmiti@zsoft-consulting.com"),
            cvList = listOf(Attachment(url = "old_url", name = "old_url"))
        )

        val expectedCandidate = Candidate(
            candidateCode = candidateCode,
            company = company,
            firstName = "mh",
            lastName = "lek",
            address = "old address",
            country = "Algeria",
            applicationType = "sourcing",
            source = "linkedIn",
            phoneNumbers = listOf("0753571104"),
            emails = emptyList(),
            tags = listOf( "java"),
            cvList = emptyList(),
            otherAttachments = emptyList(),
            socialNetworks = listOf(SocialNetwork(type = "linkedin", link = "linkedin_url"))
        )


        // then
        assertThat(oldCandidate.delete(candidateDataToBeDeleted)).isEqualTo(expectedCandidate)

    }
}