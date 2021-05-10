package com.github.newsapp.di

import android.content.Context
import com.github.newsapp.domain.usecases.timestamp.ThreetenTimeUseCase
import com.github.newsapp.domain.usecases.timestamp.TimestampUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TimeStampModule {
    @Provides
    @Singleton
    fun getTimeStampRepository(context: Context): TimestampUseCase {
        return ThreetenTimeUseCase(context)
    }
}