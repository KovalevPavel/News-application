package com.github.newsapp.di

import com.github.newsapp.data.remote.retrofit.RetrofitApi
import com.github.newsapp.domain.usecases.filesystem.FileSystemUseCase
import com.github.newsapp.domain.usecases.timestamp.TimestampUseCase
import com.github.newsapp.presenters.NewsPresenter
import com.github.newsapp.ui.fragments.newsDetailsFragment.NewsDetailsFragment
import com.github.newsapp.ui.fragments.newsFragment.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TimeStampModule::class,
        AppContextModule::class,
        NetworkModule::class,
        UseCasesModule::class
    ]
)
interface NewsApplicationComponent {
    fun getFileSystemUseCase(): FileSystemUseCase
    fun inject(presenter: NewsPresenter)
    fun inject(fragment: MainFragment)
    fun inject(fragment: NewsDetailsFragment)
    fun getRetrofitApi(): RetrofitApi
    fun getTimeStampUseCase(): TimestampUseCase
}