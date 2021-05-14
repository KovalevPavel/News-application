package com.github.newsapp.domain.entities

import com.github.newsapp.domain.usecases.loadingnews.NewsTypes
import com.squareup.moshi.JsonClass
//Класс для получения от сервера деталей определенной новости
@JsonClass(generateAdapter = true)
data class NewsItemExtended(
    val id: Long,
    val title: String,
    val description: String,
    val type: NewsTypes,
    val publishedAt: Long,
    var publishedAtString: String = "",
    val images: List<ImageToLoad>? = null,
    val shareText: String? = null
)

@JsonClass(generateAdapter = true)
data class ImageToLoad(
    val name: String,
    val url: String
)