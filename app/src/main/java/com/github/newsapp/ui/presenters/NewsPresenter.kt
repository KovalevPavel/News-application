package com.github.newsapp.ui.presenters

import android.view.View
import com.github.newsapp.di.ComponentObject
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.HeaderItem
import com.github.newsapp.domain.usecases.UserNameUseCase
import com.github.newsapp.domain.usecases.network.NetworkUseCase
import com.github.newsapp.ui.CiceroneScreens
import com.github.newsapp.ui.view.NewsPageView
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

/**
 * Презентер для экрана со списком новостей
 * @property launchNumber порядковый номер запуска приложения
 * @property loadingUseCase интерактор для операций с загрузкой данных с сервера
 * @property userNameUseCase интерактор для операций с именем пользователя
 * @property router роутер Cicerone, отвечающий за навигацию внутри приложения
 * @property newsList список новостей
 * @property currentPageNumber номер текущей загруженной страницы
 * @property rateDialogActivated флаг, указывающий на видимость диалога рейтинга
 */
@InjectViewState
class NewsPresenter : MvpPresenter<NewsPageView>(), PresenterWithRetry, WithUpButton {

    private var launchNumber = 0

    private val networkScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var loadingUseCase: NetworkUseCase

    @Inject
    lateinit var userNameUseCase: UserNameUseCase

    @Inject
    lateinit var router: Router

    private var newsList = emptyList<DisplayInRecycleItem>()
    private var currentPageNumber = 0
    private var rateDialogActivated = false

    init {
        injectDependencies()
    }

    private fun translateLaunchNumber (number: Int) {
        launchNumber = number
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
//        вводим задержку для того, чтобы успела отыграться анимация перехода при первом запуске
        CoroutineScope(Dispatchers.Main).launch {
            if (launchNumber == 1)
                delay(1000)
            loadNews()
            showRatingDialog()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyDependencies()
        networkScope.cancel()
    }

    private fun injectDependencies() {
        ComponentObject.apply {
            addNewsComponent()
            newsComponent?.inject(this@NewsPresenter)
        }
    }

    private fun destroyDependencies() {
        ComponentObject.clearNewsComponent()
    }

    /**
     * Загрузка списка новостей
     */
    suspend fun loadNews() {
        loadingUseCase.getNews(
            onSuccess = {
//            Загружаем первую страницу списка новостей
                networkScope.launch {
                    loadingUseCase.loadPortion(1)
                    {
                        currentPageNumber = 1
                        newsList = it
                        newsList = listOf(HeaderItem()) + newsList
                        viewState.updateNewsList(newsList)
                    }
                }
            },
            onFail =
            {
                dismissRatingDialog()
                router.navigateTo(CiceroneScreens.retryScreen(this@NewsPresenter))
            })
    }

    /**
     * Показ диалог рейтинга
     */
    private fun showRatingDialog() {
        if (!rateDialogActivated) {
            rateDialogActivated = true
            viewState.toggleRatingDialog(true)
        }
    }

    /**
     * Скрытие диалога рейтинга
     */
    private fun dismissRatingDialog() {
        if (rateDialogActivated) {
            rateDialogActivated = false
            viewState.toggleRatingDialog(false)
        }
    }

    /**
     * Показ фрагмента с начальной анимацией
     */
    fun revealFragment(view: View) {
        viewState.showRevealAnim(view)
    }

    /**
     * Отправка очередной страницы списка новостей во view
     */
    fun loadNextPage() {
        if (loadingUseCase.everythingIsLoaded) return
        CoroutineScope(Dispatchers.Main).launch {
            viewState.togglePageLoading(true)
            delay(2000)
            viewState.togglePageLoading(false)
            getNextPage()
        }
    }

    /**
     * Загрузка очередной страницы списка новостей
     */
    private fun getNextPage() {
        currentPageNumber += 1
        networkScope.launch {
            loadingUseCase.loadPortion(
                currentPageNumber
            ) {
                newsList = newsList + it
                viewState.updateNewsList(newsList)
            }
        }
    }

    /**
     * Навигация к деталям новости
     */
    fun navigateToDetails(newsId: Long) {
        router.navigateTo(CiceroneScreens.newsDetailsScreen(newsId))
    }

    override fun retryLoading() {
        networkScope.launch {
            viewState.togglePageLoading(true)
            loadNews()
            showRatingDialog()
        }
    }

    override fun toggleUpButton(toggle: Boolean) {
        viewState.toggleUpButton(toggle)
    }
}