package com.github.newsapp.di.components.subcomponents

import com.github.newsapp.di.scopes.MainActivityScope
import com.github.newsapp.ui.presenters.ActivityPresenter
import dagger.Subcomponent

/**
 * Главный компонент приложения
 */
@Subcomponent
@MainActivityScope
interface MainActivityComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): MainActivityComponent
    }

    fun inject(activityPresenter: ActivityPresenter)
}