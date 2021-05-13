package com.github.newsapp.presenters

import android.view.View
import com.github.newsapp.NewsApplication
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.HeaderItem
import com.github.newsapp.domain.usecases.filesystem.FileSystemUseCase
import com.github.newsapp.domain.usecases.loadingnews.LoadingUseCase
import com.github.newsapp.ui.CiceroneScreens
import com.github.newsapp.ui.view.NewsPageView
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
) : MvpPresenter<NewsPageView>(), PresenterWithRetry, WithUpButton {

    init {
        NewsApplication.newsApplicationComponent.inject(this)
    }

    var launchNumber = 0

    @Inject
    lateinit var loadingUseCase: LoadingUseCase

    @Inject
    lateinit var filesystemUseCase: FileSystemUseCase

    private var newsList = emptyList<DisplayInRecycleItem>()
    private var currentPageNumber = 0
    private var rateDialogActivated = false

    fun loadNews() {
        loadingUseCase.loadNews({
            loadingUseCase.loadPortion(1, {
                currentPageNumber = 1
                newsList = it
                newsList = listOf(HeaderItem()) + newsList
                viewState.updateNewsList(newsList)
            }, {
                dismissRatingDialog()
                router.navigateTo(CiceroneScreens.retryScreen(this))
            })
        }, {
            dismissRatingDialog()
            router.navigateTo(CiceroneScreens.retryScreen(this))
        })
    }

    private fun dismissRatingDialog() {
        if (rateDialogActivated) {
            rateDialogActivated = false
            viewState.toggleRatingDialog(false)
        }
    }

    fun revealFragment(view: View) {
        viewState.showRevealAnim(view)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
//        вводим задержку для того, чтобы успела отыграться анимация перехода при первом запуске
        viewState.translateLaunchNumber()
        CoroutineScope(Dispatchers.Main).launch {
            if (launchNumber == 1)
                delay(1000)
            loadNews()
            showRatingDialog()
        }
    }

    private fun showRatingDialog() {
        if (!rateDialogActivated) {
            rateDialogActivated = true
            viewState.toggleRatingDialog(true)
        }
    }

    fun loadNextPage() {
        if (loadingUseCase.everythingIsLoaded) return
        CoroutineScope(Dispatchers.Main).launch {
            viewState.toggleLoading(true)
            delay(2000)
            viewState.toggleLoading(false)
            getNextPage()
        }
    }

    private fun getNextPage() {
        currentPageNumber += 1
        loadingUseCase.loadPortion(currentPageNumber, {
            newsList = newsList + it
            viewState.updateNewsList(newsList)
        }, {
            router.navigateTo(CiceroneScreens.retryScreen(this))
        })
    }

    fun navigateToDetails(newsId: Long) {
        router.navigateTo(CiceroneScreens.newsDetailsScreen(newsId))
    }

    override fun retryLoading() {
        viewState.loadNewsList()
        showRatingDialog()
    }

    override fun toggleUpButton(toggle: Boolean) {
        viewState.toggleUpButton(toggle)
    }
}