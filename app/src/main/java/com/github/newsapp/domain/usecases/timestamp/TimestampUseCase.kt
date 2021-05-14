package com.github.newsapp.domain.usecases.timestamp

//Интерфейс для классов, описывающих операции по работе с временными метками
interface TimestampUseCase {
    fun getCurrentTimestamp(): Long
    fun getPublishedAtString(publishedAt: Long): String
    fun getCurrentTime(): TimeOfTheDay
    fun makeTimeOfTheDayString(): String
}