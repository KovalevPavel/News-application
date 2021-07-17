package com.github.newsapp.data

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.github.newsapp.app.NewsApplication
import com.github.newsapp.di.qualifiers.packageName.FakePackageName
import javax.inject.Inject

class ExternalConnectionRepository @Inject constructor(
    private val activity: NewsApplication,
    @FakePackageName private val packageName: String
) {
    fun openGooglePlay() {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            )
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
            activity.startActivity(intent)
        }
    }
}