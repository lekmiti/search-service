package com.lekmiti.searchservice.infrastructure.services

import com.lekmiti.searchservice.domain.search.ResponseModel
import com.lekmiti.searchservice.domain.search.SearchRequestModel
import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.search.SearchService
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.unit.Fuzziness
import org.elasticsearch.index.query.MultiMatchQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service

@Service
class ElasticSearchService(private val client: RestHighLevelClient) : SearchService {
    override fun searchForCandidates(
        searchRequestModel: SearchRequestModel,
        scopes: Collection<String>
    ): ResponseModel<Candidate> {

        val query = QueryBuilders
            .multiMatchQuery(searchRequestModel.term, * scopes.toTypedArray())
            .fuzziness(Fuzziness.AUTO)

        val highlighter = HighlightBuilder()
        scopes.forEach {highlighter.field(HighlightBuilder.Field(it)) }

        val source = SearchSourceBuilder()
            .query(query)
            .from(searchRequestModel.pageable.offset.toInt())
            .size(searchRequestModel.pageable.pageSize)
            .trackScores(true)
            .highlighter(highlighter)

        val searchRequest = SearchRequest()
            .indices(searchRequestModel.client)
            .source(source)

        val searchResult = client.search(searchRequest, RequestOptions.DEFAULT)

        val totalItems = searchResult.hits.totalHits?.value ?: 0

        val items = searchResult.toCandidatesAsItems()

        val pageImpl = PageImpl(items, searchRequestModel.pageable, totalItems)

        return ResponseModel(
            scope = "candidates",
            items = items,
            pagination = pageImpl.toPagination(totalItems.toInt())
        )
    }

}