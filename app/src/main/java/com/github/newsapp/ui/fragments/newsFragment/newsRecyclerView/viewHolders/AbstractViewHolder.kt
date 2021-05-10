package com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders

import android.content.Context
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.github.newsapp.NewsApplication
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.NewsItem

abstract class AbstractViewHolder(
    private val binder: ViewBinding,
    private val context: Context? = null,
    onClickListener: ((Int) -> Unit)? = null
) : RecyclerView.ViewHolder(binder.root) {
    companion object {
        const val SHARED_NAME = "shared_name"
        const val USER_NAME = "user_name"
    }
    val timestampUseCase = NewsApplication.newsApplicationComponent.getTimeStampUseCase()
    val fileSystemUseCase = NewsApplication.newsApplicationComponent.getFileSystemUseCase()

    init {
        setSelectableBackground()
        onClickListener?.let {newListener ->
            itemView.setOnClickListener {
                newListener (adapterPosition)
            }
        }
    }
    open fun bind(item: DisplayInRecycleItem) {
        if ((item as? NewsItem)?.isEnabled == true) {
            setSelectableBackground()
        }
    }

    //устанавливаем кликабельный бэкграунд на элементе
    private fun setSelectableBackground() {
        val outValue = TypedValue()
        context?.theme?.resolveAttribute(android.R.attr.selectableItemBackground, outValue, false)
        binder.root.setBackgroundResource(outValue.resourceId)
    }
}