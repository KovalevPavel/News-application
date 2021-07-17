package com.github.newsapp.di.modules

import com.github.newsapp.BuildConfig
import com.github.newsapp.di.qualifiers.packageName.FakePackageName
import com.github.newsapp.di.qualifiers.packageName.RealPackageName
import dagger.Module
import dagger.Provides

/**
 * Модуль, содержащий методы, предназначенные для получения имени пакета приложения
 */
@Module
class PackageNameModule {

    @Provides
    @RealPackageName
    fun providePackageName(): String {
        return BuildConfig.APPLICATION_ID
    }

    @Provides
    @FakePackageName
    fun provideFakePackageName(): String {
        return "com.spotify.music"
    }
}