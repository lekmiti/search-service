package com.lekmiti.searchservice.infrastructure.services

import com.google.gson.Gson
import com.lekmiti.searchservice.domain.CandidateCode
import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.candidate.CandidateService
import com.lekmiti.searchservice.domain.search.Item
import com.lekmiti.searchservice.domain.search.ItemMetaData
import com.lekmiti.searchservice.infrastructure.restapi.SearchResource
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions.DEFAULT
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType.JSON
import org.elasticsearch.index.query.QueryBuilders.termQuery
import org.elasticsearch.index.query.TermQueryBuilder
import org.elasticsearch.index.reindex.DeleteByQueryRequest
import org.elasticsearch.index.reindex.UpdateByQueryRequest
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*


private val gson = Gson()

private val log = LoggerFactory.getLogger(SearchResource::class.java)

@Service
class ElasticCandidateService(private val client: RestHighLevelClient) : CandidateService {


    override fun findCandidateByCode(candidateCode: CandidateCode, index: String): Candidate? {
        val sourceBuilder = SearchSourceBuilder().query(termQuery("candidateCode.keyword", candidateCode))
        val searchRequest = SearchRequest().indices(index).source(sourceBuilder)
        return client
            .search(searchRequest, DEFAULT)
            .toCandidate()
    }

    override fun saveCandidate(candidate: Candidate, index: String): Candidate {
        val request = IndexRequest(index)
            .id(UUID.randomUUID().toString())
            .source(gson.toJson(candidate), JSON)
        client.index(request, DEFAULT)
        return candidate
    }

    override fun updateCandidate(candidate: Candidate, index: String): Candidate {
        val request = UpdateByQueryRequest(index)
            .setQuery(TermQueryBuilder("candidateCode.keyword", candidate.candidateCode))
        client.updateByQuery(request, DEFAULT)
        return candidate
    }

    override fun deleteCandidate(candidateCode: CandidateCode, index: String) {
        val request = DeleteByQueryRequest(index)
            .setQuery(TermQueryBuilder("candidateCode.keyword", candidateCode))
        client.deleteByQuery(request, DEFAULT)
    }
}

fun SearchResponse.toCandidate(): Candidate? =
    hits.hits.firstOrNull()?.sourceAsString.let { gson.fromJson(it, Candidate::class.java) }

fun SearchHit.toCandidateAsItem(): Item<Candidate> =
    Item(
        item = sourceAsString.let { gson.fromJson(it, Candidate::class.java) },
        itemMetaData = toItemMetaData()
    )

fun SearchResponse.toCandidatesAsItems(): List<Item<Candidate>> =
    hits.hits.map { it.toCandidateAsItem() }

fun SearchHit.toItemMetaData() = ItemMetaData(
    score = score.toBigDecimal(),
    highlight = highlightFields.values.map { it.fragments.toString() }.toList()
)


