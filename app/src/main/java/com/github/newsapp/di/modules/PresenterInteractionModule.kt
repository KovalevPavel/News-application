package com.github.newsapp.di.modules

import com.github.newsapp.domain.usecases.viewpagerInteraction.ViewPagerInteraction
import com.github.newsapp.domain.usecases.viewpagerInteraction.ViewPagerInteractionImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterInteractionModule {
    @Provides
    @Singleton
    fun provideViewPagerInteraction(): ViewPagerInteraction {
        return ViewPagerInteractionImpl()
    }
}