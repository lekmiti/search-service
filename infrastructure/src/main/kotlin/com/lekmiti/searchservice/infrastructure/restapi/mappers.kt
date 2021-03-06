package com.lekmiti.searchservice.infrastructure.services

import com.lekmiti.searchservice.domain.search.Links
import com.lekmiti.searchservice.domain.search.Pagination
import org.springframework.data.domain.PageImpl
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


fun <T> PageImpl<T>.toPagination(totalItems: Int) = Pagination(
    size = size,
    page = number,
    totalPages = totalPages,
    totalItems = totalItems,
    isFirst = isFirst,
    isLast = isLast,
    hasNext = hasNext(),
    hasPrevious = hasPrevious(),
    sort = sort,
    links = Links(
        first = constructPageUri(0),
        last = constructPageUri(totalPages - 1),
        self = constructPageUri(number),
        previous = if (hasPrevious()) constructPageUri(number - 1) else null,
        next = if (hasNext()) constructPageUri(number + 1) else null
    )
)



fun constructPageUri(page: Int): String {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .replaceQueryParam("page", page)
        .build()
        .encode()
        .toUriString()
}
