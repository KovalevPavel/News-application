package com.github.newsapp.ui.fragments.newsFragment.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.github.newsapp.ui.presenters.NewsPresenter

class NewsOnScrollListener (private val newsPresenter: NewsPresenter): RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!recyclerView.canScrollVertically(1)) {
            newsPresenter.loadNextPage()
        }
    }
}