package com.github.newsapp.domain.usecases.loadingnews

import com.github.newsapp.data.remote.NetworkService
import com.github.newsapp.domain.entities.NewsItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoadingUseCaseImpl(networkRepository: NetworkService) : LoadingUseCase(networkRepository) {

    override fun loadNews(onLoadComplete: () -> Unit, onError: (t: Throwable) -> Unit) {
        networkRepository.getNews({
            onLoadComplete()
        }, {
            onError (it)
        })
    }

    override fun loadPortion(
        currentPageNumber: Int,
        onLoadComplete: (List<NewsItem>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        networkRepository.loadNewPortion(currentPageNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newPortion ->
                onLoadComplete(newPortion)
            }, {
                onError(it)
            })
    }
}