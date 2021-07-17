package com.github.newsapp.data.networkRepository.remote.retrofit

import com.github.newsapp.data.entities.RecItemExtended
import com.github.newsapp.data.entities.ServerResponseItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface NetworkApi {
    @GET("/newsfeed")
    fun getNewsList(): Single<ServerResponseItem>

    @GET("/newsfeed/{newsID}")
    fun getNewsDetails(
        @Header("Keanu: Reeves")
        @Path("newsID") newsID: Long
    ): Single<RecItemExtended>
}