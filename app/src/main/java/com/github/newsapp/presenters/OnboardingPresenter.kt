package com.github.newsapp.presenters

import androidx.fragment.app.FragmentActivity
import com.github.newsapp.R
import com.github.newsapp.ui.fragments.newsFragment.MainFragment
import com.github.newsapp.ui.view.OnboardingView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class OnboardingPresenter(private val parentActivity: FragmentActivity) :
    MvpPresenter<OnboardingView>() {

    fun checkCurrentScreen(lastScreen: Boolean) {
        val btnText = if (lastScreen) "Начать" else "Пропустить"
        viewState.setButtonText(btnText)
    }

    fun startUsing() {
        parentActivity.supportFragmentManager.apply {
            beginTransaction()
                .add(R.id.fragment_view, MainFragment())
                .commit()
            popBackStack()
        }
    }
}