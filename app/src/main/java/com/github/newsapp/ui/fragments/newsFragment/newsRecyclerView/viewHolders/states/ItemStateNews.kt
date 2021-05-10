package com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders.states

import android.content.Context
import com.github.newsapp.R
import com.github.newsapp.databinding.ItemNewsBinding
import com.github.newsapp.util.getColor

class ItemStateNews(override val binder: ItemNewsBinding, override val context: Context) :
    ItemState(binder, context) {
    override var imageContainer = binder.imgNews
    override fun prepareViewHolder(imageUrl: String?) {
        //очищаем фоновое изображение
        binder.imgBackground.setImageResource(0)
        showSmallImage(imageUrl != null)
        imageUrl?.let {
            loadImage(it)
        }
        binder.apply {
            getColor(context, R.color.black).also {
                textPublishedAt.setTextColor(it)
                textItemType.setTextColor(it)
                textNewsHeader.setTextColor(it)
                textNewsDescription.setTextColor(it)
            }
        }
    }
}