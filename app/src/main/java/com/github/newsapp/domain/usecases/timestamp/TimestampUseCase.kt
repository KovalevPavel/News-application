package com.github.newsapp.domain.usecases.timestamp

interface TimestampUseCase {
    fun getCurrentTimestamp(): Long
    fun getPublishedAtString(publishedAt: Long): String
    fun getCurrentTime(): TimeOfTheDay
    fun makeTimeOfTheDayString(): String
}