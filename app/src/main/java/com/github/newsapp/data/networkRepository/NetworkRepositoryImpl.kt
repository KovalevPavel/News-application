package com.github.newsapp.data.networkRepository

import com.github.newsapp.data.entities.RecItemExtended
import com.github.newsapp.data.networkRepository.remote.retrofit.NetworkApi
import com.github.newsapp.domain.entities.NewsItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Имплементация репозитория загрузки данных с сервера
 * @param networkApi экземпляр Api
 */
class NetworkRepositoryImpl(
    private val networkApi: NetworkApi
) : NetworkRepository {
    private var newsList = emptyList<NewsItem>()
    private var end = 0

    override suspend fun getNews(
        onSuccess: () -> Unit,
        onFail: (t: Throwable) -> Unit
    ) {
        networkApi.getRecordsList()
            .map {
                it.newsList
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                newsList = it
                onSuccess()
            }, {
                onFail(it)
            })
    }

    override suspend fun getNewsDetails(
        newsID: Long,
        onSuccess: (RecItemExtended) -> Unit,
        onFail: (t: Throwable) -> Unit
    ) {
        networkApi.getRecorDetails(newsID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it)
            }, {
                onFail(it)
            })
    }

    override suspend fun loadNewPortion(
        loadingPage: Int,
        onFinish: (List<NewsItem>) -> Unit
    ) {
        withContext(Dispatchers.IO) {
//            начальный индекс новости в загружаемой странице
            val start = (loadingPage - 1) * 20
//            конечный индекс нововсти в загружаемой странице
            end = loadingPage * 20 - 1

            try {
                newsList[end]
            } catch (e: IndexOutOfBoundsException) {
                end = newsList.size - 1
            }
            val resultList = newsList.filterIndexed { index, _ ->
                index in (start..end)
            }
            /*
            Имитация получения данных с сервера.
            По хорошему, необходимо вынести отдельную функцию в Api
             */
            if (loadingPage != 1)
                delay(2000L)

            onFinish(resultList)
        }
    }

    override fun notifyEverythingIsLoaded(): Boolean {
        return end == newsList.size - 1
    }
}