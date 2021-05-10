package com.github.newsapp.util

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.viewpager2.widget.ViewPager2
import com.github.newsapp.BuildConfig
import kotlinx.coroutines.delay

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

fun ViewPager2.autoscroll(lifecycleScope: LifecycleCoroutineScope, delay: Long) {
    lifecycleScope.launchWhenResumed {
        scrollIndefinitely(delay)
    }
}

private suspend fun ViewPager2.scrollIndefinitely(interval: Long) {
    delay(interval)
    val numberOfItems = adapter?.itemCount ?: 0
    val lastIndex = if (numberOfItems > 0) numberOfItems - 1 else 0
    val nextItem = if (currentItem == lastIndex) 0 else currentItem + 1

    setCurrentItem(nextItem, true)

    scrollIndefinitely(interval)
}