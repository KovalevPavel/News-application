package com.github.newsapp.domain.usecases

import com.github.newsapp.data.timestampRepository.TimeOfTheDay
import com.github.newsapp.data.timestampRepository.TimestampRepository
import javax.inject.Inject

class TimestampUseCase @Inject constructor(private val timeStampRepo: TimestampRepository) {
    fun getCurrentTimestamp(): Long {
        return timeStampRepo.getCurrentTimestamp()
    }

    fun getPublishedAtString(publishedAt: Long): String {
        return timeStampRepo.getPublishedAtString(publishedAt)
    }

    fun getCurrentTime(): TimeOfTheDay {
        return timeStampRepo.getCurrentTime()
    }

    fun makeTimeOfTheDayString(): String {
        return timeStampRepo.makeTimeOfTheDayString()
    }
}