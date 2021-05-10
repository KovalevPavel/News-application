package com.github.newsapp.ui.fragments.newsFragment.ratedialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.github.newsapp.NewsApplication
import com.github.newsapp.databinding.DialogRateBinding

class RateAlertDialog(context: Context, isFirstLaunch: Boolean = false) : AlertDialog(context) {
    private val rateDialogBinder = DialogRateBinding.inflate(LayoutInflater.from(context))
    private var rating = 0F

    private val filesystemUseCase = NewsApplication.newsApplicationComponent.getFileSystemUseCase()

    init {
        setView(rateDialogBinder.root)
        setRateButtonState(isFirstLaunch)
        rateDialogBinder.ratingBar.setOnRatingBarChangeListener { _, newRating, _ ->
            setRateButtonState(true)
            rating = newRating
        }
        rateDialogBinder.btnRate.setOnClickListener {
            filesystemUseCase.setRating(rating)
            this.dismiss()
        }
    }

    fun setRating(rating: Float) {
        rateDialogBinder.ratingBar.rating = rating
    }

    private fun setRateButtonState(isFirstLaunch: Boolean) {
        rateDialogBinder.btnRate.isEnabled = isFirstLaunch
    }
}