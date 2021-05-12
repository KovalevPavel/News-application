package com.github.newsapp.di.modules

import com.github.newsapp.data.remote.NetworkService
import com.github.newsapp.data.remote.RetrofitService
import com.github.newsapp.data.remote.retrofit.FakeRetrofitApi
import com.github.newsapp.data.remote.retrofit.RetrofitApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class NetworkModule {
    companion object {
        private const val BASE_URL = "https://www.example.com"
        private const val HEADER_DEVICE = "DeviceName"
        private const val HEADER_TIMEZONE = "TimeZone"
    }

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                request.newBuilder()
                    .addHeader(HEADER_DEVICE, android.os.Build.MODEL)
                    .addHeader(HEADER_TIMEZONE, TimeZone.getDefault().id)
                    .build()
                it.proceed(request)
            }
            .build()
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
    fun getNetworkApi(retrofit: Retrofit): RetrofitApi {
//        return retrofit.create()
        return FakeRetrofitApi()
    }

    @Provides
    @Singleton
    fun getNetworkService(retrofit: RetrofitApi): NetworkService {
        return RetrofitService(retrofit)
    }
}