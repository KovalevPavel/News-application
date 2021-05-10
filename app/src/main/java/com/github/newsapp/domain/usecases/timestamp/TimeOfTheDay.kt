package com.github.newsapp.domain.usecases.timestamp

import androidx.annotation.StringRes
import com.github.newsapp.R

enum class TimeOfTheDay (@StringRes val stringRes: Int) {
    MORNING (R.string.greetings_morning),
    DAY (R.string.greetings_day),
    EVENING (R.string.greetings_evening)
}