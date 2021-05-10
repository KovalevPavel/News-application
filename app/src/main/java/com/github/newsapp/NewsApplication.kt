package com.github.newsapp

import android.app.Application
import com.github.newsapp.di.AppContextModule
import com.github.newsapp.di.DaggerNewsApplicationComponent
import com.github.newsapp.di.NewsApplicationComponent
import com.github.terrakok.cicerone.Cicerone
import com.jakewharton.threetenabp.AndroidThreeTen

class NewsApplication : Application() {
    companion object {
        lateinit var instance: NewsApplication
            private set
        lateinit var newsApplicationComponent: NewsApplicationComponent
    }

    private val cicerone = Cicerone.create()
    val router = cicerone.router
    val navigatorHolder = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        initiateDagger()
        AndroidThreeTen.init(this)
        instance = this
    }

    private fun initiateDagger() {
        newsApplicationComponent = DaggerNewsApplicationComponent
            .builder()
            .appContextModule(AppContextModule(this))
            .build()
    }
}