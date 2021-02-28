package com.lekmiti.searchservice.infrastructure

import com.lekmiti.searchservice.domain.CandidateCode
import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.search.Item
import com.lekmiti.searchservice.domain.search.ItemMetaData
import java.math.BigDecimal


object CandidateForTest {
    fun aCandidateItem(candidateCode: CandidateCode) = Item(
        item = Candidate(candidateCode = candidateCode, company = "zsoft-consulting"),
        itemMetaData = ItemMetaData(score = BigDecimal.ONE)
    )
}



