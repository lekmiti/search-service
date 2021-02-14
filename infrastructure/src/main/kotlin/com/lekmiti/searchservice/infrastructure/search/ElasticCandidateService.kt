package com.lekmiti.searchservice.infrastructure.search

import com.google.gson.Gson
import com.lekmiti.searchservice.domain.CandidateCode
import com.lekmiti.searchservice.domain.Item
import com.lekmiti.searchservice.domain.RequestModel
import com.lekmiti.searchservice.domain.ResponseModel
import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.candidate.CandidateService
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions.DEFAULT
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.unit.Fuzziness
import org.elasticsearch.common.xcontent.XContentType.JSON
import org.elasticsearch.index.query.QueryBuilders.matchQuery
import org.elasticsearch.index.query.QueryBuilders.termQuery
import org.elasticsearch.index.query.TermQueryBuilder
import org.elasticsearch.index.reindex.DeleteByQueryRequest
import org.elasticsearch.index.reindex.UpdateByQueryRequest
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder
import org.springframework.data.domain.PageImpl
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service
import java.util.*

private val gson = Gson()

@Service
class ElasticCandidateService(
    private val elasticsearchRestTemplate: ElasticsearchRestTemplate,
    private val elasticsearchHighLevelClient: RestHighLevelClient
) : CandidateService {

    override fun searchForCandidates(requestModel: RequestModel): ResponseModel<Candidate> {
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
            pagination = pageImpl.toPagination(searchResult.totalHits.toInt())
        )
    }

    override fun findCandidateByCode(candidateCode: CandidateCode, index: String): Candidate? {
        val sourceBuilder = SearchSourceBuilder().query(termQuery("candidateCode", candidateCode))
        val searchRequest = SearchRequest().indices(index).source(sourceBuilder)
        return elasticsearchHighLevelClient
                .search(searchRequest, DEFAULT)
                .toCandidate()
    }

    override fun saveCandidate(candidate: Candidate, index: String): Candidate {
        val request = IndexRequest(index)
            .id(UUID.randomUUID().toString())
            .source(gson.toJson(candidate), JSON)
        elasticsearchHighLevelClient.index(request, DEFAULT)
        return candidate

    }

    override fun updateCandidate(candidate: Candidate, index: String): Candidate {
        val request = UpdateByQueryRequest(index)
            .setQuery(TermQueryBuilder("candidateCode", candidate.candidateCode))
        elasticsearchHighLevelClient.updateByQuery(request, DEFAULT)
        return candidate
    }

    override fun deleteCandidate(candidateCode: CandidateCode, index: String) {
        val request = DeleteByQueryRequest(index)
            .setQuery(TermQueryBuilder("candidateCode", candidateCode))
        elasticsearchHighLevelClient.deleteByQuery(request, DEFAULT)
    }

}

fun SearchResponse.toCandidate(): Candidate? =
    hits.hits.firstOrNull()?.sourceAsString.let { gson.fromJson(it, Candidate::class.java) }


fun SearchHit<EsCandidate>.toCandidate() = Candidate(
    firstName = content.firstName,
    company = content.company,
    lastName = content.lastName,
    cvList = content.cvList,
    emails = content.emails,
    candidateCode = content.candidateCode,
    phoneNumbers = content.phoneNumbers,
    socialNetworks = content.socialNetworks,
    tags = content.tags,
    country = content.country,
    otherAttachments = content.otherAttachments
)

fun SearchHit<EsCandidate>.toItem() = Item(item = toCandidate(), itemMetaData = toItemMetaData())

