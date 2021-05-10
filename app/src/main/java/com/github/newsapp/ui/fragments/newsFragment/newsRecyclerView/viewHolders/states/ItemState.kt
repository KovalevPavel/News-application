package com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders.states

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.github.newsapp.R
import com.github.newsapp.databinding.ItemNewsBinding
import com.squareup.picasso.Picasso

abstract class ItemState internal constructor(
    open val binder: ItemNewsBinding,
    open val context: Context
) {
    abstract var imageContainer: ImageView
    abstract fun prepareViewHolder(imageUrl: String?)

    fun showSmallImage(show: Boolean) {
        binder.newsImgContainer.visibility = if (show) View.VISIBLE else View.GONE
        binder.divider.visibility = if (show) View.INVISIBLE else View.GONE
    }

    fun loadImage(imageUrl: String) {
        binder.imgNews.setImageResource(0)
        binder.imgBackground.setImageResource(0)
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.ic_image_not_supported)
            .into(imageContainer)
    }
}