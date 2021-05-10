package com.github.newsapp.domain.usecases.filesystem

import android.content.Context
import android.content.SharedPreferences
import com.github.newsapp.R

class FileSystemUseCaseImpl(val context: Context) : FileSystemUseCase {
    companion object {
        private const val PREF_NAME = "shared_prefs"
        private const val LAUNCH_NUMBER = "launch_number"
        private const val USER_NAME = "user_name"
        private const val RATING = "rating"
    }

    private fun getSharedPref(): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun getLaunchNumber(): Int {
        val sPrefs = getSharedPref()
        return sPrefs.getInt(LAUNCH_NUMBER, 0)
    }

    override fun increaseLaunchNumber(oldNumber: Int) {
        val sPrefs = getSharedPref()
        sPrefs.edit()
            .putInt(LAUNCH_NUMBER, oldNumber + 1)
            .apply()
    }

    override fun setUserName(userName: String) {
        val sPrefs = getSharedPref()
        sPrefs.edit()
            .putString(USER_NAME, userName)
            .apply()
    }

    override fun getRating(): Float {
        val startRating = -1F
        val sPrefs = getSharedPref()
        return sPrefs.getFloat(RATING, startRating)
    }

    override fun setRating(rating: Float) {
        val sPrefs = getSharedPref()
        sPrefs.edit()
            .putFloat(RATING, rating)
            .apply()
    }

    override fun getUserName(): String {
        val defaultName = context.resources.getString(R.string.user_default_name)
        val sPrefs = getSharedPref()
        return sPrefs.getString(USER_NAME, defaultName) ?: defaultName
    }
}