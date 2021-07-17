package com.github.newsapp.data.networkRepository.local

import com.github.newsapp.data.entities.RecItemExtended
import com.github.newsapp.data.entities.ServerResponseItem
import com.github.newsapp.data.networkRepository.remote.retrofit.NetworkApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Имплементация Api (для тестов)
 * @param responseFactory экземлпяр фабрики генерации ответов сервера
 */
class FakeNetworkApi @Inject constructor(private val responseFactory: ServerResponseFactory) :
    NetworkApi {
    /*
    Если оставить как есть, в ходе работы время от времени будет генерироваться ошибка.
    Если закомментить предполследнюю строку в функциях, будет отрабатываться последняя строка (ошибка не генерируется).
     */
    override fun getNewsList(): Single<ServerResponseItem> {
        return Single.create {
            val serverResponse =
                responseFactory.generateServerResponse()
//            it.onSuccess(if (Random.nextBoolean()) serverResponse else null)
            it.onSuccess(serverResponse)
        }
    }

    override fun getNewsDetails(newsID: Long): Single<RecItemExtended> {
        return Single.create {
            val newsItemExtended =
                responseFactory.generateNewsItemExtended(newsID)
//            it.onSuccess(if (Random.nextBoolean()) newsItemExtended else null)
            it.onSuccess(newsItemExtended)
        }
    }
}