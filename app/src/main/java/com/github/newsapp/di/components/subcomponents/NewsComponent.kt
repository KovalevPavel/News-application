package com.github.newsapp.di.components.subcomponents

import com.github.newsapp.di.modules.PackageNameModule
import com.github.newsapp.di.modules.TimeStampModule
import com.github.newsapp.di.scopes.NewsScope
import com.github.newsapp.ui.fragments.newsFragment.NewsFragment
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders.HeaderItemViewHolder
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders.NewsItemViewHolder
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders.states.ItemState
import com.github.newsapp.ui.presenters.NewsDetailsPresenter
import com.github.newsapp.ui.presenters.NewsPresenter
import dagger.Subcomponent

/**
 * Субкомпонент, предоставляющий зависимости для работы со списком новостей
 */
@Subcomponent(
    modules = [
        PackageNameModule::class,
        TimeStampModule::class
    ]
)
@NewsScope
interface NewsComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): NewsComponent
    }

    fun inject(presenter: NewsPresenter)
    fun inject (presenter: NewsDetailsPresenter)
    fun inject(fragment: NewsFragment)
    fun inject(viewHolder: HeaderItemViewHolder)
    fun inject (viewHolder: NewsItemViewHolder)
    fun inject (viewState: ItemState)
}