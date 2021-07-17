package com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders.states

import com.github.newsapp.R
import com.github.newsapp.databinding.ItemNewsBinding
import com.github.newsapp.util.getColor

class ItemStateAlert(override val binder: ItemNewsBinding) :
    ItemState(binder) {
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
            getColor(stateContext, textColor).also {
                textPublishedAt.setTextColor(it)
                textItemType.setTextColor(it)
                textNewsHeader.setTextColor(it)
                textNewsDescription.setTextColor(it)
            }
        }
    }
}