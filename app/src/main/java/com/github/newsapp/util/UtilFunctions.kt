package com.github.newsapp.util

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.newsapp.BuildConfig

fun loggingDebug (debugLine: String) {
    if (BuildConfig.DEBUG)
        Log.d("loggingDebug", debugLine)
}

fun getColor(context: Context, @ColorRes color: Int): Int {
    return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) ContextCompat.getColor(
        context,
        color
    )
    else ResourcesCompat.getColor(context.resources, color, null)
}