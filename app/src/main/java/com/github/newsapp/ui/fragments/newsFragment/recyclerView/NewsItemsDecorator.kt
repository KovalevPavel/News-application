package com.github.newsapp.ui.fragments.newsFragment.recyclerView

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.newsapp.app.NewsApplication
import javax.inject.Inject

class NewsItemsDecorator @Inject constructor(private val appContext: NewsApplication): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val offset = 8.fromDpToPixels(appContext)
        with(outRect) {
            left = offset
            bottom = offset/2
            right = offset
            top = offset/2
        }
    }

    private fun Int.fromDpToPixels (context: Context): Int {
        val density = context.resources.displayMetrics.densityDpi
        val pixelsInDp = density/ DisplayMetrics.DENSITY_DEFAULT
        return this*pixelsInDp
    }
}