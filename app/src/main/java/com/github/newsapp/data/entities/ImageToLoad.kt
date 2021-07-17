package com.github.newsapp.data.entities

import com.squareup.moshi.JsonClass

/**
 * Загружаемое с сервера изображение
 * @param name имя изображения
 * @param url ссылка на изображение
 */
@JsonClass(generateAdapter = true)
class ImageToLoad(
    val name: String,
    val url: String
)