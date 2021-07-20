package com.github.newsapp.di.modules

import com.github.newsapp.ui.cicerone.NewsRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Модуль, прдоставляющий объекты, необходимые для навигации между экранами приложения
 */
@Module
class NavigationModule {
    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<NewsRouter>): NewsRouter {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigatorHolder(cicerone: Cicerone<NewsRouter>): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}