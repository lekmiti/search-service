package com.lekmiti.searchservice.domain

abstract class Candidate(
        val condidateNbr: CandidateCode,
        val firstName: String? = "anonymous",
        val lastName: String? = "anonymous",
        val email: String? = "anonymous@gmail.com",
        open val cv: CV? = null
)