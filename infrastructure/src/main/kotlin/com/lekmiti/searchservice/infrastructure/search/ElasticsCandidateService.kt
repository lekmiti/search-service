package com.lekmiti.searchservice.infrastructure.search

import com.google.gson.Gson
import com.lekmiti.searchservice.domain.*
import com.lekmiti.searchservice.infrastructure.persistence.ElasticsearchCandidateRepository
import org.elasticsearch.common.unit.Fuzziness
import org.elasticsearch.index.query.QueryBuilders.matchQuery
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder
import org.springframework.data.domain.PageImpl
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.document.Document
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.core.query.UpdateQuery
import org.springframework.stereotype.Service


@Service
class ElasticsCandidateService(
    private val elasticsearchRestTemplate: ElasticsearchRestTemplate,
    private val elasticsearchCandidateRepository: ElasticsearchCandidateRepository,
    private val gson: Gson
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

    override fun findCandidateByCode(candidateCode: CandidateCode) =
        elasticsearchCandidateRepository.findByCandidateCode(candidateCode)


    override fun saveCandidate(candidate: Candidate) =
        elasticsearchCandidateRepository.save(candidate.toEsCandidate())


    override fun updateCandidate(candidate: Candidate) =
        elasticsearchCandidateRepository.findByCandidateCode(candidate.candidateCode) // this extra find is to get the doc id from
            ?.let {
                val updateQuery = UpdateQuery.builder(it.id!!)
                                    .withDocAsUpsert(true)
                                    .withUpsert(Document.parse(gson.toJson(candidate)))
                                    .build()
                elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of("candidates"))
                return@let candidate}
            ?: saveCandidate(candidate)
}

fun Candidate.toEsCandidate() = EsCandidate(
    candidateCode, firstName, lastName, email, phoneNumber, socialNetworks, tags, country, otherAttachments, cvList
)

fun SearchHit<EsCandidate>.toCandidate() = Candidate(
    firstName = content.firstName,
    lastName = content.lastName,
    cvList = content.cvList,
    email = content.email,
    candidateCode = content.candidateCode,
    phoneNumber = content.phoneNumber,
    socialNetworks = content.socialNetworks,
    tags = content.tags,
    country = content.country,
    otherAttachments = content.otherAttachments
)

fun SearchHit<EsCandidate>.toItem() = Item(item = toCandidate(), itemMetaData = toItemMetaData())


