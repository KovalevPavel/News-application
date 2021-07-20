package com.github.newsapp.data

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LaunchNumberRepository @Inject constructor(private val sPrefs: SharedPreferences) {
    suspend fun getLaunchNumber(): Int {
        return withContext(Dispatchers.IO)
        {
            sPrefs.getInt(FileSystemConstants.LAUNCH_NUMBER, 1)
        }
    }

    suspend fun increaseLaunchNumber() {
        withContext(Dispatchers.IO) {
            val currentLaunchNumber = getLaunchNumber()
            sPrefs.edit()
                .putInt(FileSystemConstants.LAUNCH_NUMBER, currentLaunchNumber + 1)
                .apply()
        }
    }
}