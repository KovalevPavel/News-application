package com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.newsapp.databinding.ItemNewsBinding
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.NewsItem
import com.github.newsapp.domain.usecases.timestamp.TimestampUseCase
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders.NewsItemViewHolder
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class NewsItemsAdapterDelegate(
    private val context: Context,
    private val onClickListener: (Int) -> Unit
) : AbsListItemAdapterDelegate<NewsItem, DisplayInRecycleItem, NewsItemViewHolder>() {

    override fun isForViewType(
        item: DisplayInRecycleItem,
        items: MutableList<DisplayInRecycleItem>,
        position: Int
    ): Boolean {
        return item is NewsItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): NewsItemViewHolder {
        val inflater = LayoutInflater.from(context)
        val binder = ItemNewsBinding.inflate(inflater, parent, false)
        return NewsItemViewHolder(binder, context, onClickListener)
    }

    override fun onBindViewHolder(
        item: NewsItem,
        holder: NewsItemViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}