package com.github.newsapp.domain.usecases.timestamp

import android.content.Context
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.MonthResources
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class ThreetenTimeUseCase(val context: Context) : TimestampUseCase {
    override fun getCurrentTimestamp(): Long {
        return Instant.now().toEpochMilli()
    }

    override fun getPublishedAtString(publishedAt: Long): String {
        val timestamp = Instant.ofEpochMilli(publishedAt)

        val publishMonth = MonthResources.valueOf(
            "M_${getDateTimeString("MM", timestamp)}"
        ).stringRes
        val monthString = context.resources.getString(publishMonth)

        val dateString = getDateTimeString("dd.yyyy", timestamp)
        return dateString.replace(".", " $monthString ")
    }

    override fun getCurrentTime(): TimeOfTheDay {
        val timestamp = Instant.ofEpochMilli(getCurrentTimestamp())
        val currentTime = getDateTimeString("HH:mm", timestamp)
        val time = try {
            currentTime.substringBefore(':').toInt() to
                    currentTime.substringAfter(':').toInt()
        } catch (e: IllegalArgumentException) {
            0 to 0
        }
        if (time.first < 12) return TimeOfTheDay.MORNING
        return if (time.first >= 18) TimeOfTheDay.EVENING
        else TimeOfTheDay.DAY
    }

    override fun makeTimeOfTheDayString() = context.resources.getString(getCurrentTime().stringRes)

    private fun getDateTimeString(format: String, timestamp: Instant): String {
        return DateTimeFormatter.ofPattern(format)
            .withZone(ZoneId.systemDefault())
            .format(timestamp)
    }
}