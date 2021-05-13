package com.github.newsapp.presenters

import com.github.newsapp.NewsApplication
import com.github.newsapp.ui.view.RateDialogView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class RateDialogPresenter: MvpPresenter<RateDialogView>() {

    private val fileSystemUseCase = NewsApplication.newsApplicationComponent.getFileSystemUseCase()

    fun saveRating(newRating: Float) {
        fileSystemUseCase.setRating(newRating)
    }

    fun getRating(): Float {
        return fileSystemUseCase.getRating()
    }

    fun closeDialog() {
        viewState.dismissDialog()
    }
}