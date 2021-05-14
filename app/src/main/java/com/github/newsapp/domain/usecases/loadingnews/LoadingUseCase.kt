package com.github.newsapp.domain.usecases.loadingnews

import com.github.newsapp.data.remote.NetworkService
import com.github.newsapp.domain.entities.NewsItem

//Абстрактный класс для классов, описывающих методы по загрузке данных с сервера
abstract class LoadingUseCase(open val networkRepository: NetworkService) {
    val everythingIsLoaded: Boolean
        get() = networkRepository.notifyEverythingIsLoaded()

    abstract fun loadNews(onLoadComplete: () -> Unit, onError: (t: Throwable) -> Unit)
    abstract fun loadPortion(
        currentPageNumber: Int,
        onLoadComplete: (List<NewsItem>) -> Unit,
        onError: (t: Throwable) -> Unit
    )
}