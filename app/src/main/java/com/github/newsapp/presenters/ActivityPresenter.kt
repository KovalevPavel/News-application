package com.github.newsapp.presenters

import com.github.newsapp.NewsApplication
import com.github.newsapp.ui.fragments.newsFragment.NewsFragment
import com.github.newsapp.ui.fragments.onboardingFragment.OnboardingFragment
import com.github.newsapp.ui.view.ActivityView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ActivityPresenter : MvpPresenter<ActivityView>() {
    private val fileSystemUseCase = NewsApplication.newsApplicationComponent.getFileSystemUseCase()

//    замена фрагмента в контейнере
    fun replaceFragment() {
        val fragment =
            if (checkOnboardNeeded()) OnboardingFragment() else NewsFragment()
        viewState.navigateToFragment(fragment)
        fileSystemUseCase.setOnboardingNeeded(false)
    }

//    проверка, нужно ли показывать onvoarding
    private fun checkOnboardNeeded(): Boolean {
        return fileSystemUseCase.getOnboardingNeeded()
    }
}