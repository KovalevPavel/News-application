package com.github.newsapp.presenters

import com.github.newsapp.NewsApplication
import com.github.newsapp.ui.view.RetryScreenView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class RetryPresenter : MvpPresenter<RetryScreenView>() {
    private val router = NewsApplication.instance.router
    fun retryLoading() {
        viewState.navigateBack()
        router.exit()
    }
}