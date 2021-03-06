package com.github.newsapp.ui.fragments.newsFragment.recyclerView.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.HeaderItem
import com.github.newsapp.domain.entities.NewsItem
import com.github.newsapp.ui.presenters.WithUpButton
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class NewsRecyclerAdapter(
    private val fragmentWithUpButtonPresenter: WithUpButton,
    onClickListener: (Int) -> Unit
) : AsyncListDifferDelegationAdapter<DisplayInRecycleItem>(DiffUtilItemCallback()) {

    private var upButtonIsShown = false

    init {
        delegatesManager
            .addDelegate(NewsItemsAdapterDelegate(onClickListener))
            .addDelegate(HeaderItemAdapterDelegate())
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        val needToShowUpButton = position !in (0 until 11)

        if (needToShowUpButton && !upButtonIsShown)
            showUpButton()

        if (!needToShowUpButton && upButtonIsShown)
            hideUpButton()
    }

    private fun showUpButton() {
        upButtonIsShown = true
        fragmentWithUpButtonPresenter.toggleUpButton(true)
    }

    private fun hideUpButton() {
        upButtonIsShown = false
        fragmentWithUpButtonPresenter.toggleUpButton(false)
    }

    class DiffUtilItemCallback : DiffUtil.ItemCallback<DisplayInRecycleItem>() {
        override fun areItemsTheSame(
            oldItem: DisplayInRecycleItem,
            newItem: DisplayInRecycleItem
        ): Boolean {
            if (oldItem::class != newItem::class) return false
            if (oldItem is HeaderItem && newItem is HeaderItem) return true
            oldItem as NewsItem
            newItem as NewsItem
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DisplayInRecycleItem,
            newItem: DisplayInRecycleItem
        ): Boolean {
            return oldItem.funCompare(newItem)
        }
    }

    fun getNewsID(position: Int): Long? {
        return (items[position] as? NewsItem)?.id
    }
}