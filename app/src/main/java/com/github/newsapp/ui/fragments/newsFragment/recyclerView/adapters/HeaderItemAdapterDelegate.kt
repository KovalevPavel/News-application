package com.github.newsapp.ui.fragments.newsFragment.recyclerView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.newsapp.databinding.ItemNewsHeaderBinding
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.HeaderItem
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders.HeaderItemViewHolder
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class HeaderItemAdapterDelegate :
    AbsListItemAdapterDelegate<HeaderItem, DisplayInRecycleItem, HeaderItemViewHolder>() {
    override fun isForViewType(
        item: DisplayInRecycleItem,
        items: MutableList<DisplayInRecycleItem>,
        position: Int
    ): Boolean {
        return item is HeaderItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): HeaderItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = ItemNewsHeaderBinding.inflate(inflater, parent, false)
        return HeaderItemViewHolder(binder)
    }

    override fun onBindViewHolder(
        item: HeaderItem,
        holder: HeaderItemViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}