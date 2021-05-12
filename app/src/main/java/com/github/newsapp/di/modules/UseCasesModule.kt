package com.github.newsapp.di.modules

import android.content.Context
import com.github.newsapp.data.remote.NetworkService
import com.github.newsapp.domain.usecases.filesystem.FileSystemUseCase
import com.github.newsapp.domain.usecases.filesystem.FileSystemUseCaseImpl
import com.github.newsapp.domain.usecases.loadingnews.LoadingUseCase
import com.github.newsapp.domain.usecases.loadingnews.LoadingUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {
    @Provides
    @Singleton
    fun provideLoadingUseCase(networkService: NetworkService): LoadingUseCase {
        return LoadingUseCaseImpl(networkService)
    }

    @Provides
    fun provideFileSystemUseCase (context: Context): FileSystemUseCase {
        return FileSystemUseCaseImpl (context)
    }
}