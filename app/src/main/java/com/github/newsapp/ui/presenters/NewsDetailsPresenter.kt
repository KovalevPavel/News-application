package com.github.newsapp.ui.presenters

import android.content.Intent
import com.github.newsapp.app.NewsApplication
import com.github.newsapp.data.entities.RecItemExtended
import com.github.newsapp.di.ComponentObject
import com.github.newsapp.domain.usecases.TimestampUseCase
import com.github.newsapp.domain.usecases.network.NetworkUseCase
import com.github.newsapp.domain.usecases.viewpagerInteraction.ViewPagerInteraction
import com.github.newsapp.ui.CiceroneScreens
import com.github.newsapp.ui.view.NewsDetailsView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Презентер для экрана детальной информации о новости
 * @property viewPagerFullScreenInteractor интерактор для связи с полноэкранным просмотром
 * @property router роутер Cicerone, отвечающий за навигацию внутри приложения
 * @property newsID id текущей новости
 * @property currentRecDetails текущий объект новости
 * @property imageCount число изображений, полученных с сервера
 * @property currentImageIndex индекс текущего изображения
 * @property disposableCounter счетчик изображения (текущее изображение во viewPager)
 */
@InjectViewState
class NewsDetailsPresenter : MvpPresenter<NewsDetailsView>(), PresenterWithRetry {

    private val networkScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var appContext: NewsApplication

    @Inject
    lateinit var viewPagerFullScreenInteractor: ViewPagerInteraction

    @Inject
    lateinit var networkUseCase: NetworkUseCase

    @Inject
    lateinit var timestampUseCase: TimestampUseCase

    @Inject
    lateinit var router: Router

    private lateinit var currentRecDetails: RecItemExtended

    private val imageCount: Int?
        get() = currentRecDetails.images?.size

    private var currentImageIndex: Int
        get() = viewPagerFullScreenInteractor.currentScreen
        set(value) {
            viewPagerFullScreenInteractor.currentScreen = value
        }

    private var disposableCounter: Disposable?
        get() = viewPagerFullScreenInteractor.disposableCounter
        set(value) {
            viewPagerFullScreenInteractor.disposableCounter = value
        }

    private var newsID: Long? = null

    init {
        injectDependencies()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadDetails()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyDependencies()
    }

    private fun injectDependencies() {
        ComponentObject.apply {
            addNewsComponent()
            newsComponent?.inject(this@NewsDetailsPresenter)
        }
    }

    private fun destroyDependencies() {
        ComponentObject.clearNewsComponent()
    }

    private fun loadDetails() {
        networkScope.launch {
            newsID?.let { newsID ->
                networkUseCase.getNewsDetails(newsID,
                    { recItem ->
                        currentRecDetails = recItem
                        timestampUseCase.getPublishedAtString(recItem.publishedAt).also {
                            recItem.publishedAtString = it
                        }
                        viewState.bindDetails(recItem)
                        viewState.setImageToViewPager(currentImageIndex)
                    }, {
                        router.navigateTo(CiceroneScreens.retryScreen(this@NewsDetailsPresenter))
                    })
            }
        }
    }

    /**
     * Обновление текущего изображения
     */
    fun updatePreviewImage() {
        viewState.setImageToViewPager(currentImageIndex)
    }

    /**
     * Открытие полноэкранного просмотра изображений
     */
    fun openFullScreenView() {
        disposableCounter?.dispose()
        viewPagerFullScreenInteractor.apply {
            imageList = currentRecDetails.images.orEmpty()
            currentScreen = currentImageIndex
        }
        router.navigateTo(CiceroneScreens.fullScreenViewPager())
    }

    /**
     * Функция таймера для автоматического скролла изображений
     */
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


    /**
     * Получение id новости для загрузки
     */
    fun updateNewsID(updatedID: Long) {
        newsID = updatedID
    }

    /**
     * Возвращение на предыдущий экран
     */
    fun navigateBack() {
        disposableCounter?.dispose()
        router.exit()
    }

    /**
     * Запуск диалога "поделиться"
     */
    fun shareNews() {
        val shareText = currentRecDetails.shareText
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        appContext.startActivity(shareIntent)
    }

    override fun retryLoading() {
        loadDetails()
    }
}