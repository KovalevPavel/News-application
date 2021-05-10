package com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders.states

import android.content.Context
import com.github.newsapp.R
import com.github.newsapp.databinding.ItemNewsBinding
import com.github.newsapp.util.getColor

class ItemStateAlert(override val binder: ItemNewsBinding, override val context: Context) :
    ItemState(binder, context) {
    override var imageContainer = binder.imgBackground
    override fun prepareViewHolder(imageUrl: String?) {
        //очищаем фоновое изображение
        binder.imgBackground.setImageResource(0)
        showSmallImage(false)
        imageUrl?.let {
            loadImage(it)
        }
        binder.apply {
            val textColor = if (imageUrl == null) R.color.black else R.color.white
            getColor(context, textColor).also {
                textPublishedAt.setTextColor(it)
                textItemType.setTextColor(it)
                textNewsHeader.setTextColor(it)
                textNewsDescription.setTextColor(it)
            }
        }
    }
}