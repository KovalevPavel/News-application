package com.github.newsapp.presenters

import com.github.newsapp.ui.view.RetryScreenView
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class RetryPresenter (private val router: Router) : MvpPresenter<RetryScreenView>() {
    fun retryLoading() {
//        viewState.navigateBack()
        router.exit()
    }
}