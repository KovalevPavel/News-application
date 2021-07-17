package com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders.states

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.github.newsapp.R
import com.github.newsapp.app.NewsApplication
import com.github.newsapp.databinding.ItemNewsBinding
import com.github.newsapp.di.ComponentObject
import com.squareup.picasso.Picasso
import javax.inject.Inject

abstract class ItemState internal constructor(
    open val binder: ItemNewsBinding,
//    open val context: Context
) {
    init {
        injectDependencies()
    }
    @Inject
    lateinit var stateContext: NewsApplication

    abstract var imageContainer: AppCompatImageView
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

    private fun injectDependencies() {
        ComponentObject.apply {
            addNewsComponent()
            newsComponent?.inject(this@ItemState)
        }
    }
}