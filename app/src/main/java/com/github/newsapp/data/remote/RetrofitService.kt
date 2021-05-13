package com.github.newsapp.data.remote

import com.github.newsapp.NewsApplication
import com.github.newsapp.data.remote.retrofit.RetrofitApi
import com.github.newsapp.domain.entities.NewsItem
import com.github.newsapp.domain.entities.NewsItemExtended
import com.github.newsapp.util.loggingDebug
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitService(
    private val retrofit: RetrofitApi
) : NetworkService {
    //список загруженных новостей
    private var newsList = emptyList<NewsItem>()
    private var end = 0

    private val timestampUseCase = NewsApplication.newsApplicationComponent.getTimeStampUseCase()

    override fun getNews(
        onSuccess: () -> Unit,
        onFail: (t: Throwable) -> Unit
    ) {
        retrofit.getNewsList()
            .map {
                it.newsList.forEach { newsItem ->
                    newsItem.publishedAtString =
                        timestampUseCase.getPublishedAtString(newsItem.publishedAt)
                }
                it.newsList
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                newsList = it
                loggingDebug("size: ${newsList.size}")
                onSuccess()
            }, {
                onFail(it)
            })
    }

    override fun getNewsDetails(
        newsID: Long,
        successAction: (NewsItemExtended) -> Unit,
        onFail: (t: Throwable) -> Unit
    ) {
        retrofit.getNewsDetails(newsID)
            .map {
                it.publishedAtString = timestampUseCase.getPublishedAtString(it.publishedAt)
                it
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successAction(it)
            }, {
                onFail(it)
            })
    }

    /*
    функция пагинации
    pageNum - номер загружаемой страницы
    */
    override fun loadNewPortion(pageNum: Int): Single<List<NewsItem>> {
        //начальный индекс новости в загружаемой странице
        val start = (pageNum - 1) * 20
        //конечный индекс нововсти в загружаемой странице
        end = pageNum * 20 - 1

        try {
            newsList[end]
        } catch (e: IndexOutOfBoundsException) {
            end = newsList.size - 1
        }
        loggingDebug("$start:$end")
        val resultList = newsList.filterIndexed { index, _ ->
            index in (start..end)
        }
        return Single.just(resultList)
    }

    override fun notifyEverythingIsLoaded(): Boolean {
        return end == newsList.size - 1
    }
}