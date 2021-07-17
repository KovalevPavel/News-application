package com.github.newsapp.di.components.subcomponents

import com.github.newsapp.di.modules.TimeStampModule
import com.github.newsapp.di.scopes.NewsDetailsScope
import com.github.newsapp.ui.fragments.newsDetailsFragment.NewsDetailsFragment
import com.github.newsapp.ui.presenters.NewsDetailsPresenter
import dagger.Subcomponent

/**
 * Субкомпонент, предоставляющий зависимости для работы с одной конкретной новостью
 */
@Subcomponent (modules = [TimeStampModule::class])
@NewsDetailsScope
interface NewsDetailsComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): NewsDetailsComponent
    }

    fun inject(presenter: NewsDetailsPresenter)
    fun inject(fragment: NewsDetailsFragment)
}