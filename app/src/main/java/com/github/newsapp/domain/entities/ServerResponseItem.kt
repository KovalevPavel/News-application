package com.github.newsapp.domain.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//Класс-обертка для получения ответа от сервера в виде списка новостей
@JsonClass (generateAdapter = true)
data class ServerResponseItem(
    @Json (name = "items")
    val newsList: List <NewsItem>,
    @Json (name = "count")
    val itemsCount: Int
)