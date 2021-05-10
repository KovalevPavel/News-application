package com.github.newsapp.presenters

import com.github.newsapp.NewsApplication
import com.github.newsapp.ui.fragments.newsFragment.MainFragment
import com.github.newsapp.ui.fragments.onboardingFragment.OnboardingFragment
import com.github.newsapp.ui.view.ActivityView
import com.github.newsapp.util.loggingDebug
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ActivityPresenter : MvpPresenter<ActivityView>() {

    private val fileSystemUseCase = NewsApplication.newsApplicationComponent.getFileSystemUseCase()

    private var currentLaunchNumber: Int? = null

    fun replaceFragment() {
        val fragment =
            if (checkOnboardNeeded()) OnboardingFragment() else MainFragment.newInstance(currentLaunchNumber?:1)
        viewState.navigateToFragment(fragment)
    }

    private fun checkOnboardNeeded(): Boolean {
        return currentLaunchNumber == 1
    }

    fun loadLaunchInfo() {
        currentLaunchNumber = fileSystemUseCase.getLaunchNumber() + 1
        fileSystemUseCase.increaseLaunchNumber(fileSystemUseCase.getLaunchNumber())
    }
}