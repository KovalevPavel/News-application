package com.github.newsapp.ui.presenters

import com.github.newsapp.ui.cicerone.NewsRouter
import com.github.newsapp.ui.view.RetryScreenView
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * Презентер для экрана "Повторить загрузку"
 * @param router роутер Cicerone, отвечающий за навигацию внутри приложения
 */
@InjectViewState
class RetryPresenter(private val router: NewsRouter) : MvpPresenter<RetryScreenView>() {
    fun retryLoading() {
        viewState.navigateBack()
        router.exit()
    }
}