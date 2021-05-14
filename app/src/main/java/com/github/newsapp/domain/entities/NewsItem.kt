package com.github.newsapp.domain.entities

import com.github.newsapp.domain.usecases.loadingnews.NewsTypes
import com.squareup.moshi.JsonClass

/*
Класс элемента списка новостей
    id - идентефикатор новости
    title - заголовок новости
    description - описание новости
    type - тип элемента списка
    image - ссылка на картинку новости
    publishedAt - timestamp - дата публикации новости
    publishedAtString - переменная под строковое представление времени публикации
    isEnabled - можно ли кликнуть на элемент и перейти в детали новости
 */

@JsonClass (generateAdapter = true)
data class NewsItem(
    val id: Long,
    val title: String,
    val description: String,
    val type: NewsTypes,
    val previewImage: String? = null,
    val publishedAt: Long,
    var publishedAtString: String = "",
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
