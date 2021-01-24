package com.lekmiti.searchservice.domain

import org.springframework.data.domain.Sort
import java.math.BigDecimal

data class ResponseModel<out T>(
        val type: String,
        val items: Items<T> = emptyList(),
        val pagination: Pagination) where T : AnItem

data class ItemMetaData(val score: BigDecimal, val highlight: Collection<String>)

interface AnItem

data class Item<out T>(
        val item: T,
        val itemMetaData: ItemMetaData) where T : AnItem


data class Pagination(
        val size: Int,
        val page: Int,
        val totalItems: Int,
        val totalPages: Int,
        val hasNext: Boolean,
        val hasPrevious: Boolean,
        val isLast: Boolean,
        val isFirst: Boolean,
        val sort: Sort,
        val links: Links
)

data class Links(
        val next: String?,
        val previous: String?,
        val self: String,
        val first: String,
        val last: String)


