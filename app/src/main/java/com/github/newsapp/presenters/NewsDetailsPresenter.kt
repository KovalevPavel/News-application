package com.github.newsapp.presenters

import android.app.Activity
import android.content.Intent
import com.github.newsapp.NewsApplication
import com.github.newsapp.data.remote.NetworkService
import com.github.newsapp.domain.entities.NewsItemExtended
import com.github.newsapp.domain.usecases.viewpagerInteraction.ViewPagerInteraction
import com.github.newsapp.ui.CiceroneScreens
import com.github.newsapp.ui.view.NewsDetailsView
import com.github.newsapp.util.loggingDebug
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class NewsDetailsPresenter(
    private val activityContext: Activity,
    private val router: Router
) : MvpPresenter<NewsDetailsView>(), PresenterWithRetry {

    init {
        NewsApplication.newsApplicationComponent.inject(this)
    }

    @Inject
    lateinit var viewPagerFullScreenInteractor: ViewPagerInteraction

    @Inject
    lateinit var networkRepository: NetworkService

    //    текущий объект новостей
    private lateinit var currentNewsDetails: NewsItemExtended

    //    количество полученных с сервера изображений
    private val imageCount: Int?
        get() = currentNewsDetails.images?.size

    //    id текущего изображения
    private var currentImageIndex: Int
        get() = viewPagerFullScreenInteractor.currentScreen
        set(value) {
            viewPagerFullScreenInteractor.currentScreen = value
        }

    //    счетчик изображения (текущее изображение во viewPager)
    private var disposableCounter: Disposable?
        get() = viewPagerFullScreenInteractor.disposableCounter
        set(value) {
            viewPagerFullScreenInteractor.disposableCounter = value
        }

    //    id новости
    private var newsID: Long? = null

    private fun loadDetails() {
        newsID?.let { newsID ->
            networkRepository.getNewsDetails(newsID,
                {
                    currentNewsDetails = it
                    viewState.bindDetails(it)
                    viewState.setImageToViewPager(currentImageIndex)
                }, {
                    router.navigateTo(CiceroneScreens.retryScreen(this))
                })
        }
    }

    //    обновление текущего изображения
    fun updatePreviewImage() {
        loggingDebug(" receiving: ${currentImageIndex}")
        viewState.setImageToViewPager(currentImageIndex)
    }

    fun openFullScreenView() {
        disposableCounter?.dispose()
        viewPagerFullScreenInteractor.apply {
            imageList = currentNewsDetails.images.orEmpty()
            currentScreen = currentImageIndex
        }
        router.navigateTo(CiceroneScreens.fullScreenViewPager())
    }

    //    функция таймера
    private fun startCount() {
        disposableCounter =
            Observable.interval(ViewPagerInteraction.SCROLL_PERIOD, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
//                    логика переключения изображений
                    imageCount?.let { imgCount ->
                        currentImageIndex += 1
                        if (currentImageIndex == imgCount) currentImageIndex = 0
                        viewState.setImageToViewPager(currentImageIndex)
                    }
                }
    }

    /*
    Действия при скролле viewpager.
    - останавливаем таймер
    - меняем изображение
    - запускаем таймер заново
     */
    fun onScrollViewPager(newImageID: Int) {
        disposableCounter?.dispose()
        currentImageIndex = newImageID
        startCount()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadDetails()
    }

    //    получение id новости для загрузки
    fun updateNewsID(updatedID: Long) {
        newsID = updatedID
    }

    //    возвращение на предыдущий экран
    fun navigateBack() {
        disposableCounter?.dispose()
        router.exit()
    }

    //    запуск диалога "поделиться"
    fun shareNews() {
        val shareText = currentNewsDetails.shareText
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        activityContext.startActivity(shareIntent)
    }

    override fun retryLoading() {
        loadDetails()
    }
}