package com.github.newsapp.data.entities

import com.github.newsapp.domain.usecases.network.NewsTypes
import com.squareup.moshi.JsonClass

/**
 * Класс-обертка для получения от сервера деталей определенной записи
 * @param id id записи
 * @param title заголовок записи
 * @param description текст записи
 * @param type тип записи. Смотри [NewsTypes]
 * @param publishedAt timestamp публикации записи
 * @param publishedAtString форматированная строка даты публикации записи
 * @param images список изображений к записи
 * @param shareText текст, который отображается, когда делимся записью
 */
@JsonClass(generateAdapter = true)
data class RecItemExtended(
    val id: Long,
    val title: String,
    val description: String,
    val type: NewsTypes,
    val publishedAt: Long,
    var publishedAtString: String = "",
    val images: List<ImageToLoad>? = null,
    val shareText: String? = null
)