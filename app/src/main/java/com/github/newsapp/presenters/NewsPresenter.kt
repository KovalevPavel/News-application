package com.github.newsapp.presenters

import com.github.newsapp.NewsApplication
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.HeaderItem
import com.github.newsapp.domain.usecases.filesystem.FileSystemUseCase
import com.github.newsapp.domain.usecases.loadingnews.LoadingUseCase
import com.github.newsapp.ui.CiceroneScreens
import com.github.newsapp.ui.view.NewsPageView
import com.github.newsapp.util.loggingDebug
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class NewsPresenter(
    private val router: Router
) : MvpPresenter<NewsPageView>() {
    init {
        NewsApplication.newsApplicationComponent.inject(this)
    }

    @Inject
    lateinit var loadingUseCase: LoadingUseCase

    @Inject
    lateinit var filesystemUseCase: FileSystemUseCase

    private var newsList = emptyList<DisplayInRecycleItem>()
    private var currentPageNumber = 0

    fun loadNews() {
        loggingDebug("${newsList.isEmpty()}")
        if (newsList.isEmpty())
            loadingUseCase.loadNews({
                loadingUseCase.loadPortion(1, {
                    currentPageNumber = 1
                    newsList = it
                    newsList = listOf(HeaderItem()) + newsList
                    viewState.updateNewsList(newsList)
                }, {
                    router.navigateTo(CiceroneScreens.retryScreen())
                })
            }, {
                router.navigateTo(CiceroneScreens.retryScreen())
            })
        else viewState.updateNewsList(newsList)
    }

    fun saveRating(rating: Float) {

    }

    fun showRatingDialog(isFirstLaunch: Boolean) {
        val rating = filesystemUseCase.getRating()
        viewState.showRatingDialog(rating, isFirstLaunch)
    }

    fun loadNextPage() {
        if (loadingUseCase.everythingIsLoaded) return
        CoroutineScope(Dispatchers.Main).launch {
            viewState.showLoading()
            delay(2000)
            viewState.hideLoading()
            getNextPage()
        }
    }

    private fun getNextPage() {
        currentPageNumber += 1
        loadingUseCase.loadPortion(currentPageNumber, {
            newsList = newsList + it
            viewState.updateNewsList(newsList)
        }, {
            router.navigateTo(CiceroneScreens.retryScreen())
        })
    }

    fun navigateToDetails(newsId: Long) {
        router.navigateTo(CiceroneScreens.newsDetailsScreen(newsId))
    }

}