package com.github.newsapp.ui.presenters

import com.github.newsapp.di.ComponentObject
import com.github.newsapp.domain.usecases.RatingUseCase
import com.github.newsapp.ui.view.RateDialogView
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class RateDialogPresenter : MvpPresenter<RateDialogView>() {

    private val fileScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var ratingUseCase: RatingUseCase

    init {
        injectDependencies()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDependencies()
        fileScope.cancel()
    }

    private fun injectDependencies() {
        ComponentObject.addRatingComponent()
        ComponentObject.ratingComponent?.inject(this)
    }

    private fun clearDependencies() {
        ComponentObject.clearRatingComponent()
    }

    fun saveRating(newRating: Float) {
        fileScope.launch {
            ratingUseCase.setRating(newRating)
        }
    }

    fun getRating(onGetRating: (Float) -> Unit) {
        fileScope.launch {
            val rating = ratingUseCase.getRating()

            withContext(Dispatchers.Main) {
                onGetRating(rating)
            }
        }
    }

    fun closeDialog() {
        viewState.dismissDialog()
    }
}