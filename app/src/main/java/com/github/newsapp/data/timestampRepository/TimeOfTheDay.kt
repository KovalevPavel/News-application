package com.github.newsapp.data.timestampRepository

import androidx.annotation.StringRes
import com.github.newsapp.R

//Класс для определения времени суток в привествтенной строке
enum class TimeOfTheDay (@StringRes val stringRes: Int) {
    MORNING (R.string.greetings_morning),
    DAY (R.string.greetings_day),
    EVENING (R.string.greetings_evening)
}