package com.github.newsapp.presenters

import androidx.fragment.app.FragmentActivity
import com.github.newsapp.R
import com.github.newsapp.ui.fragments.newsFragment.MainFragment
import com.github.newsapp.ui.view.OnboardingView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class OnboardingPresenter(private val parentActivity: FragmentActivity) :
    MvpPresenter<OnboardingView>() {

    fun checkCurrentScreen(lastScreen: Boolean) {
        Single.just(lastScreen)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                changeUI(it)
            }, {

            })
    }

    private fun changeUI(lastScreen: Boolean) {
        val btnText = if (lastScreen) "Начать" else "Пропустить"
        viewState.setButtonText(btnText)
    }

    fun startUsing() {
        parentActivity.supportFragmentManager.apply {
            beginTransaction()
                .add(R.id.fragment_view, MainFragment.newInstance(1))
                .commit()
            popBackStack()
        }
    }
}