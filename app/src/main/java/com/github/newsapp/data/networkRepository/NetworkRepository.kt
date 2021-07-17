package com.github.newsapp.data.networkRepository

import com.github.newsapp.data.entities.RecItemExtended
import com.github.newsapp.domain.entities.NewsItem

/**
 * Репозиторий загрузки данных с сервера
 */
interface NetworkRepository {
    /**
     * Функция получения списка новостей
     * @param onSuccess колбэк при удачной загрузке
     * @param onFail колбэк при неудачной загрузке
     */
    suspend fun getNews(
        onSuccess: () -> Unit,
        onFail: (t: Throwable) -> Unit
    )

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
    )

    /**
     * Функция загрузки очередной страницы новостей
     * @param loadingPage загружаемая страница
     * @param onFinish колбэк при удачной загрузке
     */
    suspend fun loadNewPortion(
        loadingPage: Int,
        onFinish: (List<NewsItem>) -> Unit
    )

    /**
     * Функция оповещения о том, что все новости загружены
     */
    fun notifyEverythingIsLoaded(): Boolean
}