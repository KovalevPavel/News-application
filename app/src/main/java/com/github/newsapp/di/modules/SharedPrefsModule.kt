package com.github.newsapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.github.newsapp.app.NewsApplication
import com.github.newsapp.data.FileSystemConstants.PREF_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Модуль, предоставляющий объект SharedPreferences
 */
@Module
class SharedPrefsModule {
    @Provides
    @Singleton
    fun provideSharedPref(appContext: NewsApplication): SharedPreferences {
        return appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}