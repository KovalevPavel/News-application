package com.github.newsapp.ui.fragments.retryFragment

import android.os.Bundle
import android.view.View
import com.github.newsapp.databinding.FragmentErrorLoadingBinding
import com.github.newsapp.presenters.PresenterWithRetry
import com.github.newsapp.presenters.RetryPresenter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        нажатие на кнопку приводит к попытке повторно выполнить команду родительского презентера
        binder.btnRetry.setOnClickListener {
            retryPresenter.retryLoading()
        }
    }

    override fun navigateBack() {
        parentViewPresenter?.retryLoading()
    }
}