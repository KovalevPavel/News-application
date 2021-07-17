package com.github.newsapp.data.timestampRepository

/**
 * Интерфейс для классов, описывающих операции по работе с датой/временем
 */
interface TimestampRepository {
    fun getCurrentTimestamp(): Long
    fun getPublishedAtString(publishedAt: Long): String
    fun getCurrentTime(): TimeOfTheDay
    fun makeTimeOfTheDayString(): String
}