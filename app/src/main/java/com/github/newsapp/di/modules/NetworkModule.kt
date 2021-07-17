package com.github.newsapp.di.modules

import com.github.newsapp.data.networkRepository.NetworkRepository
import com.github.newsapp.data.networkRepository.NetworkRepositoryImpl
import com.github.newsapp.data.networkRepository.local.FakeNetworkApi
import com.github.newsapp.data.networkRepository.local.ServerResponseFactory
import com.github.newsapp.data.networkRepository.remote.retrofit.NetworkApi
import com.github.newsapp.di.qualifiers.api.LocalApi
import com.github.newsapp.di.qualifiers.api.RemoteApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.*
import javax.inject.Singleton

/**
 * Модуль, содержащий методы, предназначенные для получения объектов, необходимых для осуществления сетевого подключения
 */
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
                val oldRequest = it.request()
                val newRequest = oldRequest.newBuilder()
                    .addHeader(HEADER_DEVICE, android.os.Build.MODEL)
                    .addHeader(HEADER_TIMEZONE, TimeZone.getDefault().id)
                    .build()
                it.proceed(newRequest)
            }
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
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
    @RemoteApi
    fun getNetworkApi(retrofit: Retrofit): NetworkApi {
        return retrofit.create()
    }

    @Provides
    @Singleton
    @LocalApi
    fun providesLocalRepository(): NetworkApi {
        return FakeNetworkApi(ServerResponseFactory())
    }

    @Provides
    @Singleton
    fun getNetworkRepository(@LocalApi networkApi: NetworkApi): NetworkRepository {
        return NetworkRepositoryImpl(networkApi)
    }
}