package com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.newsapp.databinding.ItemNewsHeaderBinding
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.HeaderItem
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders.HeaderItemViewHolder
import com.github.newsapp.util.loggingDebug
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class HeaderItemAdapterDelegate (private val parentActivity: Activity) :
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
        return HeaderItemViewHolder(binder, parentActivity)
    }

    override fun onBindViewHolder(
        item: HeaderItem,
        holder: HeaderItemViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}