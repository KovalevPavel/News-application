package com.github.newsapp.ui.fragments.retryFragment

import com.github.newsapp.databinding.FragmentErrorLoadingBinding
import com.github.newsapp.presenters.RetryPresenter
import com.github.newsapp.presenters.PresenterWithRetry
import com.github.newsapp.ui.view.RetryScreenView
import com.github.newsapp.util.FragmentViewBinding
import moxy.presenter.InjectPresenter

class FragmentRetry :
    FragmentViewBinding<FragmentErrorLoadingBinding>(FragmentErrorLoadingBinding::inflate),
    RetryScreenView {

    companion object {
        fun newInstance(parentViewPresenter: PresenterWithRetry): FragmentRetry {
            val newFragment = FragmentRetry()
            newFragment.parentViewPresenter = parentViewPresenter
            return newFragment
        }
    }

    private var parentViewPresenter: PresenterWithRetry? = null

    @InjectPresenter
    lateinit var retryPresenter: RetryPresenter

    override fun onStart() {
        binder.btnRetry.setOnClickListener {
            retryPresenter.retryLoading()
        }
        super.onStart()
    }

    override fun navigateBack() {
        parentViewPresenter?.retryLoading()
    }
}