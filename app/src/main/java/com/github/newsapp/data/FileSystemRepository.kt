package com.github.newsapp.data

import android.content.SharedPreferences
import com.github.newsapp.data.FileSystemConstants.USER_NAME
import javax.inject.Inject

class FileSystemRepository @Inject constructor(private val sPrefs: SharedPreferences) {
    fun setUserName(userName: String) {
        sPrefs.edit()
            .putString(USER_NAME, userName)
            .apply()
    }

    fun getUserName(): String {
        val defaultName = "Павел"
        return sPrefs.getString(USER_NAME, defaultName) ?: defaultName
    }
}