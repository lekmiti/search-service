package com.lekmiti.searchservice.domain.search


class SearchScope {

    fun getSearchScopesForCandidates(scopes: Collection<String>) =
        if (scopes.isEmpty()) getCandidateSearchableFields
        else scopes.toList()

}

//todo: refactor this using a more dynamic pattern
private val getCandidateSearchableFields = listOf(
    "candidateCode",
    "company",
    "firstName",
    "lastName",
    "country",
    "source",
    "address",
    "applicationType",
    "phoneNumbers",
    "emails",
    "tags",
    "cvList.content",
    "cvList.name",
    "cvList.type",
    "cvList.tags",
    "otherAttachments.content",
    "otherAttachments.name",
    "otherAttachments.type",
    "otherAttachments.tags",
    "socialNetworks.link",
    "socialNetworks.type"
)



