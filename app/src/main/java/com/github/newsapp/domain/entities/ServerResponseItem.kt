package com.github.newsapp.domain.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass (generateAdapter = true)
data class ServerResponseItem(
    @Json (name = "items")
    val newsList: List <NewsItem>,
    @Json (name = "count")
    val itemsCount: Int
)