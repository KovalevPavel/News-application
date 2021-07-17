package com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.github.newsapp.domain.entities.DisplayInRecycleItem

abstract class AbstractViewHolder(
    binder: ViewBinding
) : RecyclerView.ViewHolder(binder.root) {
    abstract fun bind(item: DisplayInRecycleItem)
}