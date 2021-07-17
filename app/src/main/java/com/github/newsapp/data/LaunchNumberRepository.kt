package com.github.newsapp.data

import android.content.SharedPreferences
import com.github.newsapp.di.scopes.MainActivityScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@MainActivityScope
class LaunchNumberRepository @Inject constructor(private val sPrefs: SharedPreferences) {
    suspend fun getLaunchNumber(): Int {
        return withContext(Dispatchers.IO)
        {
            sPrefs.getInt(FileSystemConstants.LAUNCH_NUMBER, 0)
        }
    }

    suspend fun increaseLaunchNumber(oldNumber: Int) {
        withContext(Dispatchers.IO) {
            sPrefs.edit()
                .putInt(FileSystemConstants.LAUNCH_NUMBER, oldNumber + 1)
                .apply()
        }
    }

    suspend fun setOnboardingNeeded(needed: Boolean) {
        withContext(Dispatchers.IO) {
            sPrefs.edit()
                .putBoolean(FileSystemConstants.ONBOARDING, needed)
                .apply()
        }
    }

    suspend fun getOnboardingNeeded(): Boolean {
        return withContext(Dispatchers.IO) {
            sPrefs.getBoolean(
                FileSystemConstants.ONBOARDING,
                true
            )
        }
    }
}