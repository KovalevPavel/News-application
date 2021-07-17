package com.github.newsapp.ui.fragments.ratingDialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import com.github.newsapp.databinding.DialogRateBinding
import com.github.newsapp.ui.presenters.RateDialogPresenter
import com.github.newsapp.ui.view.RateDialogView
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter

class RatingDialogFragment : MvpAppCompatDialogFragment(), RateDialogView {
    @InjectPresenter
    lateinit var ratePresenter: RateDialogPresenter

    private lateinit var binder: DialogRateBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        binder = DialogRateBinding.inflate(inflater)
        bindUI()
        return AlertDialog.Builder(requireContext())
            .setView(binder.root)
            .create()
    }

    private fun bindUI() {
        binder.apply {
            ratePresenter.getRating().also {
                ratingBar.rating = it
                setRatingButtonState(it)
            }
//            настройка кнопки в зависимости от установленного рейтинга
            ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                setRatingButtonState(rating)
            }
            btnRate.setOnClickListener {
                val rating = binder.ratingBar.rating
                ratePresenter.saveRating(rating)
                ratePresenter.closeDialog()
            }
        }
    }

    private fun setRatingButtonState(rating: Float) {
        binder.btnRate.isEnabled = rating.equals(0F).not()
    }

    override fun dismissDialog() {
        dismiss()
    }
}