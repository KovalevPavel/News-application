package com.github.newsapp.data

import android.content.SharedPreferences
import com.github.newsapp.di.scopes.RatingScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@RatingScope
class RatingRepository @Inject constructor(private val sPrefs: SharedPreferences) {
    suspend fun getRating(): Float {
        return withContext(Dispatchers.IO) {
            sPrefs.getFloat(FileSystemConstants.RATING, 0F)
        }

    }

    suspend fun setRating(rating: Float) {
        withContext(Dispatchers.IO) {
            sPrefs.edit()
                .putFloat(FileSystemConstants.RATING, rating)
                .apply()
        }
    }
}