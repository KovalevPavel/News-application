package com.github.newsapp.data.remote.retrofit

import com.github.newsapp.domain.entities.NewsItemExtended
import com.github.newsapp.domain.entities.ServerResponseItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RetrofitApi {
    @GET("/newsfeed")
    fun getNewsList(): Single<ServerResponseItem>

    @GET("/newsfeed/{newsID}")
    fun getNewsDetails(
        @Header("Keanu: Reeves")
        @Path("newsID") newsID: Long
    ): Single<NewsItemExtended>
}