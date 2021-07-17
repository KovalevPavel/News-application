package com.github.newsapp.di.modules

import com.github.newsapp.app.NewsApplication
import com.github.newsapp.data.timestampRepository.TimestampRepository
import com.github.newsapp.data.timestampRepository.threeten.ThreetenTimeRepository
import dagger.Module
import dagger.Provides

/**
 * Модуль, предоставляющий репозиторий для работы с датой/временем
 */
@Module
class TimeStampModule {
    @Provides
    fun getTimeStampRepository(appContext: NewsApplication): TimestampRepository {
        return ThreetenTimeRepository(appContext)
    }
}