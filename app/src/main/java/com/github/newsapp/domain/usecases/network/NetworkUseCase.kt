package com.github.newsapp.domain.usecases.network

import com.github.newsapp.data.entities.RecItemExtended
import com.github.newsapp.data.networkRepository.NetworkRepository
import com.github.newsapp.domain.entities.NewsItem
import javax.inject.Inject

class NetworkUseCase @Inject constructor(private val networkRepository: NetworkRepository) {
    val everythingIsLoaded: Boolean
        get() = networkRepository.notifyEverythingIsLoaded()

    /**
     * Функция получения списка новостей
     * @param onSuccess колбэк при удачной загрузке
     * @param onFail колбэк при неудачной загрузке
     */
    suspend fun getNews(onSuccess: () -> Unit, onFail: (t: Throwable) -> Unit) {
        networkRepository.getNews(onSuccess, onFail)
    }

    /**
     * Функция загрузки очередной страницы новостей
     * @param loadingPage загружаемая страница
     * @param onFinish колбэк при завершении загрузки
     */
    suspend fun loadPortion(
        loadingPage: Int,
        onFinish: (List<NewsItem>) -> Unit
    ) {
        networkRepository.loadNewPortion(loadingPage, onFinish)
    }

    /**
     * Функция получения деталей определенной новости
     * @param newsID id новости
     * @param onSuccess колбэк при удачной загрузке
     * @param onFail колбэк при неудачной загрузке
     */
    suspend fun getNewsDetails(
        newsID: Long,
        onSuccess: (RecItemExtended) -> Unit,
        onFail: (t: Throwable) -> Unit
    ) {
        networkRepository.getNewsDetails(newsID, onSuccess, onFail)
    }
}