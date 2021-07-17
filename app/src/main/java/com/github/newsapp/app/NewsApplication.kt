package com.github.newsapp.app

import android.app.Application
import com.github.newsapp.di.components.DaggerNewsApplicationComponent
import com.github.newsapp.di.components.NewsApplicationComponent
import com.github.terrakok.cicerone.Cicerone
import com.jakewharton.threetenabp.AndroidThreeTen

class NewsApplication : Application() {
    companion object {
        lateinit var instance: NewsApplication
            private set

        lateinit var newsApplicationComponent: NewsApplicationComponent
            private set

        var currentLaunchNumber = 0
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initiateDagger()
        AndroidThreeTen.init(this)
        setLaunchInfo()
    }

    private fun initiateDagger() {
        newsApplicationComponent = DaggerNewsApplicationComponent
            .builder()
            .applicationContext(this)
            .cicerone(Cicerone.create())
            .build()
    }



    private fun setLaunchInfo() {
//        currentLaunchNumber = filesystemUseCase.getLaunchNumber() + 1
//        filesystemUseCase.increaseLaunchNumber(filesystemUseCase.getLaunchNumber())
    }


}