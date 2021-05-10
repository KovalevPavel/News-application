package com.github.newsapp.data.remote

import com.github.newsapp.domain.entities.NewsItem
import com.github.newsapp.domain.entities.NewsItemExtended
import io.reactivex.rxjava3.core.Single

interface NetworkService {
    fun getNews(
        onSuccess: () -> Unit,
        onFail: (t: Throwable) -> Unit
    )

    fun getNewsDetails(
        newsID: Long,
        successAction: (NewsItemExtended) -> Unit,
        onFail: (t: Throwable) -> Unit
    )

    fun loadNewPortion(pageNum: Int): Single <List<NewsItem>>
    fun notifyEverythingIsLoaded(): Boolean
}