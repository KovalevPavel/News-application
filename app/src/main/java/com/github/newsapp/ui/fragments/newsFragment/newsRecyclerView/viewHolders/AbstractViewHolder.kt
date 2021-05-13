package com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.github.newsapp.NewsApplication
import com.github.newsapp.domain.entities.DisplayInRecycleItem

abstract class AbstractViewHolder(
    binder: ViewBinding
) : RecyclerView.ViewHolder(binder.root) {
    val fileSystemUseCase = NewsApplication.newsApplicationComponent.getFileSystemUseCase()
    abstract fun bind(item: DisplayInRecycleItem)
}