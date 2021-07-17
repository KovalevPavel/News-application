package com.github.newsapp.ui.presenters

import com.github.newsapp.ui.view.RetryScreenView
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * Презентер для экрана "Повторить загрузку"
 * @param router роутер Cicerone, отвечающий за навигацию внутри приложения
 */
@InjectViewState
class RetryPresenter(private val router: Router) : MvpPresenter<RetryScreenView>() {
    fun retryLoading() {
        viewState.navigateBack()
        router.exit()
    }
}