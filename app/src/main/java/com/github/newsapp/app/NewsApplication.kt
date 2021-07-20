package com.github.newsapp.app

import android.app.Application
import com.github.newsapp.di.components.DaggerNewsApplicationComponent
import com.github.newsapp.di.components.NewsApplicationComponent
import com.github.newsapp.ui.cicerone.NewsRouter
import com.github.terrakok.cicerone.Cicerone
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class NewsApplication : Application() {
    companion object {
        lateinit var instance: NewsApplication
            private set

        lateinit var newsApplicationComponent: NewsApplicationComponent
            private set

        var currentLaunchNumber by Delegates.notNull<Int>()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initiateDagger()
        AndroidThreeTen.init(this)
        runBlocking {
            launch {
                setLaunchInfo()
            }
        }
    }

    private fun initiateDagger() {
        newsApplicationComponent = DaggerNewsApplicationComponent
            .builder()
            .applicationContext(this)
            .cicerone(Cicerone.create(NewsRouter()))
            .build()
    }

    private suspend fun setLaunchInfo() {
        currentLaunchNumber = newsApplicationComponent.launchNumberUseCase().getLaunchNumber()
        newsApplicationComponent.launchNumberUseCase().increaseLaunchNumber()
    }
}