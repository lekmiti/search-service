package com.lekmiti.searchservice.domain

import java.util.Locale.IsoCountryCode

open class Candidate(
        val condidateNbr: CandidateCode,
        val firstName: String?,
        val lastName: String?,
        val email: String?,
        val phoneNumber: String?,
        val socialNetworks: SocialNetworks,
        val tags: Tags,
        val country: Country,
        open val cv: Cv? = null) : AnItem

data class Cv(val content: String, val pathFile: String)


enum class Country(val code: IsoCountryCode, val nationality: String)

