package com.github.newsapp.di.components.subcomponents

import com.github.newsapp.di.scopes.OnboardingScope
import com.github.newsapp.ui.presenters.OnboardingPresenter
import dagger.Subcomponent

@Subcomponent
@OnboardingScope
interface OnboardingComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): OnboardingComponent
    }

    fun inject(presenter: OnboardingPresenter)
}