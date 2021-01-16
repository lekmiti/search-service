/*
package com.lekmiti.searchservice.application

import com.lekmiti.searchservice.domain.CV
import com.lekmiti.searchservice.domain.SearchService
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.Collections.singleton


object SearchAppServiceTest {

    private val searchService = mockk<SearchService>()

    private val searchAppService = SearchAppService(searchService)
    @Test
    fun `should search for Cvs referencing a given phrase`() {
        // given
        val searchablePhrase = Term("java")
        val cv1 = CV(candidateCode = "C1", pathFile = "path", firstName = "mohamed elhadi", lastName = "lekmiti")
        val cv2 = CV(candidateCode = "C2", pathFile = "path", firstName = "wafaa", lastName = "lekmiti")

        // when
        val result = searchAppService.searchCvs(singleton(searchablePhrase)).getOrThrow()

        // then
        assertThat(result).containsExactly(cv1, cv2)
    }

}

*/
