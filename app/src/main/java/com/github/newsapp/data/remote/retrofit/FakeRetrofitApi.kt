package com.github.newsapp.data.remote.retrofit

import com.github.newsapp.data.ServerResponseFactory
import com.github.newsapp.domain.entities.NewsItemExtended
import com.github.newsapp.domain.entities.ServerResponseItem
import io.reactivex.rxjava3.core.Single
import kotlin.random.Random

/*
Имплементация репозитория для тестов.
Если оставить как есть, в ходе работы время от времени будет генерироваться ошибка.
Если закомментить предполследнюю строку в функциях, будет отрабатываться последняя строка (ошибка не генерируется).
 */
class FakeRetrofitApi : RetrofitApi {

    override fun getNewsList(): Single<ServerResponseItem> {
        return Single.create {
            val serverResponse =
                ServerResponseFactory().generateServerResponse()
            val randomResult = Random.nextBoolean()
            it.onSuccess(if (randomResult) serverResponse else null)
            it.onSuccess(serverResponse)
        }
    }

    override fun getNewsDetails(newsID: Long): Single<NewsItemExtended> {
        return Single.create {
            val newsItemExtended =
                ServerResponseFactory().generateNewsItemExtended(newsID)
            val randomResult = Random.nextBoolean()
            it.onSuccess(if (randomResult) newsItemExtended else null)
            it.onSuccess(newsItemExtended)
        }
    }
}