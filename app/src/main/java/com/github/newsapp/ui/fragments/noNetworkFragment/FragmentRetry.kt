package com.github.newsapp.ui.fragments.noNetworkFragment

import com.github.newsapp.NewsApplication
import com.github.newsapp.databinding.FragmentErrorLoadingBinding
import com.github.newsapp.presenters.RetryPresenter
import com.github.newsapp.ui.view.RetryScreenView
import com.github.newsapp.util.FragmentViewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class FragmentRetry :
    FragmentViewBinding<FragmentErrorLoadingBinding>(FragmentErrorLoadingBinding::inflate),
    RetryScreenView {

//    private val parentFragment: FragmentWithRetry
//    get() = requireParentFragment() as FragmentWithRetry

    @InjectPresenter
    lateinit var retryPresenter: RetryPresenter

    @ProvidePresenter
    fun provideRetryPresenter(): RetryPresenter {
        return RetryPresenter(NewsApplication.instance.router)
    }
    override fun onStart() {
        binder.btnRetry.setOnClickListener {
            retryPresenter.retryLoading()
        }
        super.onStart()
    }

    override fun navigateBack() {
        childFragmentManager.popBackStack()
    }
}