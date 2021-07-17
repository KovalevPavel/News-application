package com.github.newsapp.domain.entities

import com.github.newsapp.domain.usecases.network.NewsTypes
import com.squareup.moshi.JsonClass

/**
 * Класс элемента списка новостей
 * @property id идентефикатор новости
 * @property title заголовок новости
 * @property description описание новости
 * @property type тип элемента списка
 * @property previewImage ссылка на картинку новости
 * @property publishedAt timestamp публикации новости
 * @property isEnabled можно ли кликнуть на элемент и перейти в детали новости
 */

@JsonClass (generateAdapter = true)
data class NewsItem(
    val id: Long,
    val title: String,
    val description: String,
    val type: NewsTypes,
    val previewImage: String? = null,
    val publishedAt: Long,
    val isEnabled: Boolean
): DisplayInRecycleItem {
    override fun funCompare(other: DisplayInRecycleItem?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsItem

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (type != other.type) return false
        if (previewImage != other.previewImage) return false
        if (publishedAt != other.publishedAt) return false
        if (isEnabled != other.isEnabled) return false

        return true
    }
}
