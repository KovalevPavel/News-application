package com.github.newsapp

import android.app.Application
import com.github.newsapp.di.DaggerNewsApplicationComponent
import com.github.newsapp.di.NewsApplicationComponent
import com.github.newsapp.di.modules.AppContextModule
import com.github.newsapp.domain.usecases.filesystem.FileSystemUseCase
import com.github.terrakok.cicerone.Cicerone
import com.jakewharton.threetenabp.AndroidThreeTen

class NewsApplication : Application() {
    companion object {
        lateinit var instance: NewsApplication
            private set
        lateinit var newsApplicationComponent: NewsApplicationComponent
        var currentLaunchNumber = 0
            private set
    }

    private val filesystemUseCase: FileSystemUseCase
        get() = newsApplicationComponent.getFileSystemUseCase()

    private val cicerone = Cicerone.create()
    val router = cicerone.router
    val navigatorHolder = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        initiateDagger()
        AndroidThreeTen.init(this)
        instance = this
        setLaunchInfo()
    }

    private fun initiateDagger() {
        newsApplicationComponent = DaggerNewsApplicationComponent
            .builder()
            .appContextModule(AppContextModule(this))
            .build()
    }

    private fun setLaunchInfo() {
        currentLaunchNumber = filesystemUseCase.getLaunchNumber() + 1
        filesystemUseCase.increaseLaunchNumber(filesystemUseCase.getLaunchNumber())
    }
}