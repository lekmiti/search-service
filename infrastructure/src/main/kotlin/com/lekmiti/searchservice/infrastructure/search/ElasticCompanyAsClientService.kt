package com.lekmiti.searchservice.infrastructure.search

import com.lekmiti.searchservice.domain.compnayasclient.CompanyAsClientService
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.client.indices.GetIndexRequest
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.xcontent.XContentType.JSON
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils


@Service
class ElasticCompanyAsClientService(
    private val client: RestHighLevelClient) : CompanyAsClientService {
    override fun initializeNewCompanyAsClient(company: String): Boolean {
        val indexExists = client.indices().exists(GetIndexRequest(company), RequestOptions.DEFAULT)
        return if (!indexExists) createNewIndex(company)
        else false
    }

    private fun createNewIndex(company: String): Boolean {
        val createIndexRequest = CreateIndexRequest(company)
        createIndexRequest.settings(
            Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2))

        val indexMapping = ResourceUtils
            .getFile("classpath:index-config/index_mapping.json")
            .bufferedReader().use { it.readText() }

        createIndexRequest.mapping(indexMapping, JSON);
        val response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT)
        return response.isAcknowledged
    }

}