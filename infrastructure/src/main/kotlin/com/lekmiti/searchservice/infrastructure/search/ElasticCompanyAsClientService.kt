package com.lekmiti.searchservice.infrastructure.search

import com.lekmiti.searchservice.domain.compnayasclient.CompanyAsClientService
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.client.indices.GetIndexRequest
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.xcontent.XContentType.JSON
import org.springframework.stereotype.Service

@Service
class ElasticCompanyAsClientService(private val client: RestHighLevelClient) : CompanyAsClientService {
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
                .put("index.number_of_replicas", 2)
        )
        // todo: refactor this to a more dynamic pattern
        createIndexRequest.mapping(
            """
           {
                  "properties": {
                    "address": {
                      "type": "text",
                      "fields": {
                        "keyword": {
                          "type": "keyword",
                          "ignore_above": 256
                        }
                      }
                    },
                    "applicationType": {
                      "type": "text",
                      "fields": {
                        "keyword": {
                          "type": "keyword",
                          "ignore_above": 256
                        }
                      }
                    },
                    "candidateCode": {
                      "type": "text",
                      "fields": {
                        "keyword": {
                          "type": "keyword",
                          "ignore_above": 256
                        }
                      }
                    },
                    "country": {
                      "type": "text",
                      "fields": {
                        "keyword": {
                          "type": "keyword",
                          "ignore_above": 256
                        }
                      }
                    },
                    "cvList": {
                      "type": "nested",
                      "include_in_parent": true
                    },
                     "otherAttachments": {
                      "type": "nested",
                      "include_in_parent": true
                    },
                    "emails": {
                      "type": "text",
                      "fields": {
                        "keyword": {
                          "type": "keyword",
                          "ignore_above": 256
                        }
                      }
                    },
                    "firstName": {
                      "type": "text",
                      "fields": {
                        "keyword": {
                          "type": "keyword",
                          "ignore_above": 256
                        }
                      }
                    },
                    "lastName": {
                      "type": "text",
                      "fields": {
                        "keyword": {
                          "type": "keyword",
                          "ignore_above": 256
                        }
                      }
                    },
                    "phoneNumbers": {
                      "type": "text",
                      "fields": {
                        "keyword": {
                          "type": "keyword",
                          "ignore_above": 256
                        }
                      }
                    },
                    "socialNetworks": {
                      "properties": {
                        "link": {
                          "type": "text",
                          "fields": {
                            "keyword": {
                              "type": "keyword",
                              "ignore_above": 256
                            }
                          }
                        },
                        "type": {
                          "type": "text",
                          "fields": {
                            "keyword": {
                              "type": "keyword",
                              "ignore_above": 256
                            }
                          }
                        }
                      }
                    },
                    "source": {
                      "type": "text",
                      "fields": {
                        "keyword": {
                          "type": "keyword",
                          "ignore_above": 256
                        }
                      }
                    },
                    "tags": {
                      "type": "text",
                      "fields": {
                        "keyword": {
                          "type": "keyword",
                          "ignore_above": 256
                        }
                      }
                    }
                  }
                }
         
            """.trimIndent()
            , JSON);
        val response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT)
        return response.isAcknowledged
    }

}