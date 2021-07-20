package com.github.newsapp.ui.presenters

import com.github.newsapp.app.NewsApplication
import com.github.newsapp.di.ComponentObject
import com.github.newsapp.domain.usecases.LaunchNumberUseCase
import com.github.newsapp.ui.cicerone.CiceroneScreens
import com.github.newsapp.ui.cicerone.NewsRouter
import com.github.newsapp.ui.view.ActivityView
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: NewsRouter

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
    suspend fun replaceFragment() {
//        scope.launch {
//            val fragment =
//                if (checkOnboardNeeded()) OnboardingFragment() else NewsFragment()
//            viewState.navigateToFragment(fragment)
//            launchNumberUseCase.setOnboardingNeeded(false)
//        }
        val screen =
            if (checkOnboardNeeded()) CiceroneScreens.onboardingScreen() else CiceroneScreens.mainFragment()
        router.navigateTo(screen)
    }

    //    проверка, нужно ли показывать onboarding
    private fun checkOnboardNeeded(): Boolean {
        return NewsApplication.currentLaunchNumber == 1
    }

    private fun initializeDependencies() {
        ComponentObject.apply {
            addMainActivityComponent()
            mainActivityComponent?.inject(this@ActivityPresenter)
        }
    }

    private fun clearDependencies() {
        ComponentObject.clearMainActivityComponent()
    }

    fun installNavigator(navigator: AppNavigator, install: Boolean) {
        if (install) navigatorHolder.setNavigator(navigator)
        else navigatorHolder.removeNavigator()
    }
}