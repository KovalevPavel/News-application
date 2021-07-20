package com.github.newsapp.di.components.subcomponents

import com.github.newsapp.di.scopes.RatingScope
import com.github.newsapp.ui.presenters.RateDialogPresenter
import dagger.Subcomponent

@Subcomponent
@RatingScope
interface RatingComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): RatingComponent
    }
    fun inject (presenter: RateDialogPresenter)
}