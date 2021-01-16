package com.lekmiti.searchservice.domain

typealias CandidateCode = String


typealias CVs = Collection<CV>

typealias Candidates = Collection<Candidate>

typealias Phrase = String

typealias Term = String

typealias Terms = Collection<Term>

fun Terms.hasOneTerm() = size == 1

