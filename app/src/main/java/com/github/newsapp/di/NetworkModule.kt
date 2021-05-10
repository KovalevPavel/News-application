package com.github.newsapp.di

import com.github.newsapp.data.remote.retrofit.FakeRetrofitApi
import com.github.newsapp.data.remote.NetworkService
import com.github.newsapp.data.remote.RetrofitService
import com.github.newsapp.data.remote.retrofit.RetrofitApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    companion object {
        private const val BASE_URL = "https://www.example.com"
    }

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun getRetrofitClient(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun getNetworkApi (retrofit: Retrofit): RetrofitApi {
//        return retrofit.create()
        return FakeRetrofitApi()
    }

    @Provides
    @Singleton
    fun getNetworkService(retrofit: RetrofitApi): NetworkService {
        return RetrofitService (retrofit)
    }
}