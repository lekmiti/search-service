package com.lekmiti.searchservice.infrastructure.search

import com.google.gson.Gson
import com.lekmiti.searchservice.domain.*
import com.lekmiti.searchservice.domain.candidate.Candidate
import com.lekmiti.searchservice.domain.candidate.CandidateService
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
class ElasticCandidateService(
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
        elasticsearchCandidateRepository.findByCandidateCode(candidate.candidateCode)
            .let {
                val updateQuery = UpdateQuery.builder(it!!.id!!)
                    .withDocument(Document.parse(gson.toJson(candidate)))
                    .build()
                elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of("candidates"))
                return@let candidate
            }

    override fun deleteCandidate(candidateCode: CandidateCode) =
        elasticsearchCandidateRepository.deleteByCandidateCode(candidateCode)

}

fun Candidate.toEsCandidate() = EsCandidate(
    candidateCode = candidateCode,
    firstName = firstName,
    lastName = lastName,
    emails = emails,
    phoneNumbers = phoneNumbers,
    socialNetworks = socialNetworks,
    tags = tags,
    country = country,
    address = address,
    applicationType = applicationType,
    source = source,
    otherAttachments = otherAttachments,
    cvList = cvList
)

fun SearchHit<EsCandidate>.toCandidate() = Candidate(
    firstName = content.firstName,
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

