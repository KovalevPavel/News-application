package com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders

import android.content.Context
import com.github.newsapp.databinding.ItemNewsBinding
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.NewsItem
import com.github.newsapp.domain.usecases.loadingnews.NewsTypes
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders.states.ItemState
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders.states.ItemStateAlert
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders.states.ItemStateNews

class NewsItemViewHolder(
    private val binder: ItemNewsBinding,
    item: NewsItem,
    private val context: Context,
    onClickListener: (Int) -> Unit
) : AbstractViewHolder(binder) {
    init {
        if (item.isEnabled)
            itemView.setOnClickListener {
                onClickListener(adapterPosition)
            }
    }

    private lateinit var holderState: ItemState

    override fun bind(item: DisplayInRecycleItem) {
        if (item !is NewsItem) return
        setState(item.type)
        holderState.prepareViewHolder(item.previewImage)
        binder.apply {
            textItemType.setText(item.type.itemHeader)
            textPublishedAt.text = item.publishedAtString
            textNewsHeader.text = item.title
            textNewsDescription.text = item.description
        }
    }

    private fun setState(newsType: NewsTypes) {
        holderState = when (newsType) {
            NewsTypes.NEWS -> ItemStateNews(binder, context)
            NewsTypes.ALERT -> ItemStateAlert(binder, context)
        }
    }
}