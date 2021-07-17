package com.github.newsapp.di

import com.github.newsapp.app.NewsApplication
import com.github.newsapp.di.components.subcomponents.FragmentRetryComponent
import com.github.newsapp.di.components.subcomponents.MainActivityComponent
import com.github.newsapp.di.components.subcomponents.NewsComponent
import com.github.newsapp.di.components.subcomponents.NewsDetailsComponent

/**
 * Объект, содержащий в себе переменные субкомпонентов, а также методы из создания и уничтожения
 */
object ComponentObject {

    var newsComponent: NewsComponent? = null
        private set

    var newsDetailsComponent: NewsDetailsComponent? = null
        private set

    var mainActivityComponent: MainActivityComponent? = null
        private set

    var retryComponent: FragmentRetryComponent? = null
        private set

    fun addNewsComponent() {
        if (newsComponent != null) return
        newsComponent = NewsApplication.newsApplicationComponent.newsComponent().build()
    }

    fun addNewsDetailsComponent() {
        if (newsDetailsComponent != null) return
        newsDetailsComponent =
            NewsApplication.newsApplicationComponent.newsDetailsComponent().build()
    }

    fun clearNewsComponent() {
        newsComponent = null
    }

    fun clearNewsDetailsComponent() {
        newsDetailsComponent = null
    }

    fun addMainActivityComponent() {
        if (mainActivityComponent != null) return
        mainActivityComponent =
            NewsApplication.newsApplicationComponent.mainActivityComponent().build()
    }

    fun clearMainActivityComponent() {
        mainActivityComponent = null
    }

    fun addRetryComponent() {
        if (retryComponent != null) return
        retryComponent =
            NewsApplication.newsApplicationComponent.retryComponent().build()
    }

    fun clearRetryComponent() {
        retryComponent = null
    }
}