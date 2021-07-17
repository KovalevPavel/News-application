package com.github.newsapp.ui.presenters

import com.github.newsapp.di.ComponentObject
import com.github.newsapp.domain.usecases.LaunchNumberUseCase
import com.github.newsapp.ui.fragments.newsFragment.NewsFragment
import com.github.newsapp.ui.fragments.onboardingFragment.OnboardingFragment
import com.github.newsapp.ui.view.ActivityView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

/**
 * Презентер для экрана активити
 * @property launchNumberUseCase интерактор для операций со значением порядкового запуска приложения
 */
@InjectViewState
class ActivityPresenter : MvpPresenter<ActivityView>() {
    @Inject
    lateinit var launchNumberUseCase: LaunchNumberUseCase

    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        initializeDependencies()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDependencies()
        scope.cancel()
    }

    //    замена фрагмента в контейнере
    fun replaceFragment() {
        scope.launch {
            val fragment =
                if (checkOnboardNeeded()) OnboardingFragment() else NewsFragment()
            viewState.navigateToFragment(fragment)
            launchNumberUseCase.setOnboardingNeeded(false)
        }
    }

    //    проверка, нужно ли показывать onboarding
    private suspend fun checkOnboardNeeded(): Boolean {
        return launchNumberUseCase.getOnboardingNeeded()
    }

    private fun initializeDependencies () {
        ComponentObject.apply {
            addMainActivityComponent()
            mainActivityComponent?.inject(this@ActivityPresenter)
        }
    }

    private fun clearDependencies() {
        ComponentObject.clearMainActivityComponent()
    }
}