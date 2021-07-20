package com.github.newsapp.ui.fragments.retryFragment

import android.os.Bundle
import android.view.View
import com.github.newsapp.databinding.FragmentErrorLoadingBinding
import com.github.newsapp.di.ComponentObject
import com.github.newsapp.ui.cicerone.NewsRouter
import com.github.newsapp.ui.presenters.PresenterWithRetry
import com.github.newsapp.ui.presenters.RetryPresenter
import com.github.newsapp.ui.view.RetryScreenView
import com.github.newsapp.util.FragmentViewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Фрагмент, отвечающиц за отображение диалога повторного подключения
 * @property router роутер Cicerone
 * @property parentViewPresenter презентер родительского объекта. Используется для трансляции команды повторного подключения.
 * @property retryPresenter презентер для экрана "Повторить подключение"
 */
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

    @Inject
    lateinit var router: NewsRouter

    private var parentViewPresenter: PresenterWithRetry? = null

    @InjectPresenter
    lateinit var retryPresenter: RetryPresenter

    @ProvidePresenter
    fun provideRetryPresenter(): RetryPresenter {
        return RetryPresenter(router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

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

    private fun injectDependencies() {
        ComponentObject.apply {
            addRetryComponent()
            retryComponent?.inject(this@FragmentRetry)
        }
    }
}