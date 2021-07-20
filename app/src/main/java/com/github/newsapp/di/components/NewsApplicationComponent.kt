package com.github.newsapp.di.components

import com.github.newsapp.app.NewsApplication
import com.github.newsapp.di.components.subcomponents.*
import com.github.newsapp.di.modules.NavigationModule
import com.github.newsapp.di.modules.NetworkModule
import com.github.newsapp.di.modules.SharedPrefsModule
import com.github.newsapp.domain.usecases.LaunchNumberUseCase
import com.github.newsapp.ui.cicerone.NewsRouter
import com.github.terrakok.cicerone.Cicerone
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SharedPrefsModule::class,
        NavigationModule::class,
        NetworkModule::class
    ]
)
interface NewsApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext (application: NewsApplication): Builder
        @BindsInstance
        fun cicerone(cicerone: Cicerone<NewsRouter>): Builder
        fun build(): NewsApplicationComponent
    }

    fun newsComponent(): NewsComponent.Builder
    fun newsDetailsComponent(): NewsDetailsComponent.Builder
    fun mainActivityComponent(): MainActivityComponent.Builder
    fun retryComponent(): FragmentRetryComponent.Builder
    fun onboardingComponent(): OnboardingComponent.Builder
    fun ratingComponent(): RatingComponent.Builder

    fun launchNumberUseCase(): LaunchNumberUseCase
}