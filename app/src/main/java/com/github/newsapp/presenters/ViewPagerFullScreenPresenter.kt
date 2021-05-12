package com.github.newsapp.presenters

import com.github.newsapp.NewsApplication
import com.github.newsapp.ui.view.ViewPagerFullScreen
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ViewPagerFullScreenPresenter : MvpPresenter<ViewPagerFullScreen>() {
    private val viewPagerInteractor =
        NewsApplication.newsApplicationComponent.getViewPagerInteractor()

    private val router = NewsApplication.instance.router

//    обновление id текущего изображения в интеракторе
    fun setNewImageID (newID: Int) {
        viewPagerInteractor.currentScreen = newID
    }

//    загрузка начальной информации из интерактора
    fun loadInitialInformation () {
        val currentID = viewPagerInteractor.currentScreen
        viewState.loadInitialInfo(currentID, viewPagerInteractor.imageList)
    }

//    возвращение на предыдущий экран
    fun navigateBack() {
        router.exit()
    }
}