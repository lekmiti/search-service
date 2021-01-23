package com.lekmiti.searchservice.domain

open class Candidate(
        val condidateNbr: CandidateCode,
        val firstName: String?,
        val lastName: String?,
        val email: String?,
        open val cv: Cv? = null
) : AnItem