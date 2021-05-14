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

//    Загрузка списка новостей
    fun loadNews() {
        loadingUseCase.loadNews({
//            Загружаем первую страницу списка новостей
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

//    показываем диалог рейтинга
    private fun showRatingDialog() {
        if (!rateDialogActivated) {
            rateDialogActivated = true
            viewState.toggleRatingDialog(true)
        }
    }

//    скрываем диалог рейтинга
    private fun dismissRatingDialog() {
        if (rateDialogActivated) {
            rateDialogActivated = false
            viewState.toggleRatingDialog(false)
        }
    }

//    показываем фрагмент с анимацией
    fun revealFragment(view: View) {
        viewState.showRevealAnim(view)
    }

//    отправка очередной страницы списка новостей во view
    fun loadNextPage() {
        if (loadingUseCase.everythingIsLoaded) return
        CoroutineScope(Dispatchers.Main).launch {
            viewState.toggleLoading(true)
            delay(2000)
            viewState.toggleLoading(false)
            getNextPage()
        }
    }

//    загрузка очередной страницы списка новостей
    private fun getNextPage() {
        currentPageNumber += 1
        loadingUseCase.loadPortion(currentPageNumber, {
            newsList = newsList + it
            viewState.updateNewsList(newsList)
        }, {
            router.navigateTo(CiceroneScreens.retryScreen(this))
        })
    }

//    навигация к деталям новости
    fun navigateToDetails(newsId: Long) {
        router.navigateTo(CiceroneScreens.newsDetailsScreen(newsId))
    }

//    повторная попытка загрузить список новостей
    override fun retryLoading() {
        viewState.loadNewsList()
        showRatingDialog()
    }

//    переключение кнопки "вверх"
    override fun toggleUpButton(toggle: Boolean) {
        viewState.toggleUpButton(toggle)
    }
}