package com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders

import com.github.newsapp.databinding.ItemNewsBinding
import com.github.newsapp.di.ComponentObject
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.NewsItem
import com.github.newsapp.domain.usecases.TimestampUseCase
import com.github.newsapp.domain.usecases.network.NewsTypes
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders.states.ItemState
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders.states.ItemStateAlert
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders.states.ItemStateNews
import javax.inject.Inject

class NewsItemViewHolder(
    private val binder: ItemNewsBinding,
    private val item: NewsItem,
    private val onClickListener: (Int) -> Unit
) : AbstractViewHolder(binder) {

    @Inject
    lateinit var timestampUseCase: TimestampUseCase

    init {
        initClick()
        injectDependencies()
    }

    private lateinit var holderState: ItemState

    override fun bind(item: DisplayInRecycleItem) {
        if (item !is NewsItem) return
        setState(item.type)
        holderState.prepareViewHolder(item.previewImage)
        binder.apply {
            textItemType.setText(item.type.itemHeader)
            textPublishedAt.text = timestampUseCase.getPublishedAtString(item.publishedAt)
            textNewsHeader.text = item.title
            textNewsDescription.text = item.description
        }
    }

    private fun initClick() {
        if (item.isEnabled)
            itemView.setOnClickListener {
                onClickListener(adapterPosition)
            }
    }

    private fun setState(newsType: NewsTypes) {
        holderState = when (newsType) {
            NewsTypes.NEWS -> ItemStateNews(binder)
            NewsTypes.ALERT -> ItemStateAlert(binder)
        }
    }

    private fun injectDependencies() {
        ComponentObject.apply {
            addNewsComponent()
            newsComponent?.inject(this@NewsItemViewHolder)
        }
    }
}