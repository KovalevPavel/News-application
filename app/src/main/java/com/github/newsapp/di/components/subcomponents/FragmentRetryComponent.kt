package com.github.newsapp.di.components.subcomponents

import com.github.newsapp.di.scopes.RetryScope
import com.github.newsapp.ui.fragments.retryFragment.FragmentRetry
import com.github.newsapp.ui.presenters.RetryPresenter
import dagger.Subcomponent

/**
 * Субкомпонент, предоставляющий необходимые зависимости для "Повторного соединения"
 */
@Subcomponent
@RetryScope
interface FragmentRetryComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): FragmentRetryComponent
    }

    fun inject(fragment: FragmentRetry)
    fun inject(presenter: RetryPresenter)
}