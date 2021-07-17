package com.github.newsapp.ui.fragments.newsFragment.recyclerView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.newsapp.databinding.ItemNewsBinding
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.NewsItem
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders.NewsItemViewHolder
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class NewsItemsAdapterDelegate(
    private val onClickListener: (Int) -> Unit
) : AbsListItemAdapterDelegate<NewsItem, DisplayInRecycleItem, NewsItemViewHolder>() {

    private var itemForVH: NewsItem? = null

    override fun isForViewType(
        item: DisplayInRecycleItem,
        items: MutableList<DisplayInRecycleItem>,
        position: Int
    ): Boolean {
        if (item is NewsItem)
            itemForVH = item
        return item is NewsItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): NewsItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = ItemNewsBinding.inflate(inflater, parent, false)
        return NewsItemViewHolder(binder, itemForVH!!, onClickListener)
    }

    override fun onBindViewHolder(
        item: NewsItem,
        holder: NewsItemViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}