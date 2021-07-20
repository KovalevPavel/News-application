package com.github.newsapp.ui.presenters

import android.view.View
import com.github.newsapp.app.NewsApplication
import com.github.newsapp.di.ComponentObject
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.HeaderItem
import com.github.newsapp.domain.usecases.UserNameUseCase
import com.github.newsapp.domain.usecases.network.NetworkUseCase
import com.github.newsapp.ui.cicerone.CiceroneScreens
import com.github.newsapp.ui.cicerone.NewsRouter
import com.github.newsapp.ui.view.NewsPageView
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

/**
 * Презентер для экрана со списком новостей
 * @property launchNumber порядковый номер запуска приложения
 * @property networkScope scope, предназначенный для запуска задач через сеть
 * @property loadingUseCase интерактор для операций с загрузкой данных с сервера
 * @property userNameUseCase интерактор для операций с именем пользователя
 * @property router роутер Cicerone, отвечающий за навигацию внутри приложения
 * @property newsList список новостей
 * @property currentPageNumber номер текущей загруженной страницы
 * @property rateDialogActivated флаг, указывающий на видимость диалога рейтинга
 */
@InjectViewState
class NewsPresenter : MvpPresenter<NewsPageView>(), PresenterWithRetry, WithUpButton {

    private val launchNumber: Int
        get() = NewsApplication.currentLaunchNumber

    private val networkScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var loadingUseCase: NetworkUseCase

    @Inject
    lateinit var userNameUseCase: UserNameUseCase

    @Inject
    lateinit var router: NewsRouter

    private var newsList = emptyList<DisplayInRecycleItem>()
    private var currentPageNumber = 0
    private var rateDialogActivated = false

    init {
        injectDependencies()
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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        CoroutineScope(Dispatchers.Main).launch {
            loadRecords()
            showRatingDialog()
        }
    }

    /**
     * Загрузка списка записей
     */
    suspend fun loadRecords() {
        viewState.toggleRecordsLoading(true)
        loadingUseCase.getNews(
            onSuccess = {
//            Загружаем первую страницу списка записей
                networkScope.launch {
                    loadingUseCase.loadPortion(1)
                    {
                        currentPageNumber = 1
                        newsList = it
                        newsList = listOf(HeaderItem()) + newsList
                        viewState.updateNewsList(newsList)
                        viewState.toggleRecordsLoading(false)
                    }
                }
            },
            onFail =
            {
                dismissRatingDialog()
                viewState.toggleRecordsLoading(false)
                router.navigateTo(CiceroneScreens.retryScreen(this@NewsPresenter))
            })
    }

    /**
     * Показ диалог рейтинга
     */
    private fun showRatingDialog() {
        if (rateDialogActivated) return
        if (launchNumber >= 3 && launchNumber % 3 == 0) {
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
        if (launchNumber == 1)
            viewState.showRevealAnim(view)
    }

    /**
     * Отправка очередной страницы списка новостей во view
     */
    fun loadNextPage() {
        if (loadingUseCase.everythingIsLoaded) return
        CoroutineScope(Dispatchers.Main).launch {
            viewState.togglePageLoading(true)
            getNextPageJob().join()
            viewState.togglePageLoading(false)
        }
    }

    /**
     * Получение объекта [Job] для загрузки очередной страницы с записями
     */
    private suspend fun getNextPageJob(): Job {
        return networkScope.launch {
            currentPageNumber += 1
            loadingUseCase.loadPortion(
                currentPageNumber
            ) {
                newsList = newsList + it
                viewState.updateNewsList(newsList)
            }
        }
    }

    /**
     * Навигация к деталям записи
     */
    fun navigateToDetails(recId: Long) {
        router.navigateTo(CiceroneScreens.newsDetailsScreen(recId))
    }

    override fun retryLoading() {
        networkScope.launch {
            viewState.togglePageLoading(true)
            loadRecords()
            showRatingDialog()
        }
    }

    override fun toggleUpButton(toggle: Boolean) {
        viewState.toggleUpButton(toggle)
    }
}