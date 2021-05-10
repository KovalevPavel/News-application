package com.github.newsapp.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppContextModule(private val context: Context) {
    @Provides
    fun providesContext() = context
}