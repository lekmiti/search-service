package com.lekmiti.searchservice.infrastructure.search

import com.lekmiti.searchservice.domain.*
import org.elasticsearch.common.unit.Fuzziness
import org.elasticsearch.index.query.QueryBuilders.matchQuery
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder
import org.springframework.data.domain.PageImpl
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service


@Service
class ElasticsSearchService(private val elasticsearchRestTemplate: ElasticsearchRestTemplate) : SearchService {


    override fun search(requestModel: RequestModel): ResponseModel<Candidate> {
        val (term, pageable) = requestModel
        val query = matchQuery("cv.content", term)
                .fuzziness(Fuzziness.AUTO)

        val searchQuery = NativeSearchQueryBuilder()
                .withQuery(query)
                .withPageable(pageable)
                .withTrackScores(true)
                .withHighlightFields(HighlightBuilder.Field("cv.content"))
                .build()

        val searchResult = elasticsearchRestTemplate.search(searchQuery, EsCandidate::class.java)

        val items = searchResult.searchHits.map { it.toItem() }


        val pageImpl = PageImpl(items, pageable, searchResult.totalHits)

        return ResponseModel(
                type = "candidates",
                items = items,
                pagination = pageImpl.toPagination(searchResult.totalHits.toInt()))
    }

}

fun SearchHit<EsCandidate>.toCandidate() = Candidate(
        firstName = content.firstName,
        lastName = content.lastName,
        cv = content.cv,
        email = content.email,
        condidateNbr = content.condidateNbr)

fun SearchHit<EsCandidate>.toItem() = Item(item = toCandidate(), itemMetaData = toItemMetaData())


