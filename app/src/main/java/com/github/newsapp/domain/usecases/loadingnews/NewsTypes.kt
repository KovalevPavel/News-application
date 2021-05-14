package com.github.newsapp.domain.usecases.loadingnews

import androidx.annotation.StringRes
import com.github.newsapp.R
import com.squareup.moshi.Json

//Класс для определения типа новости (новость/уведомление)
enum class NewsTypes (@StringRes val itemHeader: Int) {
    @Json (name = "news")
    NEWS (R.string.item_news),
    @Json (name = "alert")
    ALERT (R.string.item_alert)
}