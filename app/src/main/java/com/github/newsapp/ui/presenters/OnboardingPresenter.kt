package com.github.newsapp.ui.presenters

import com.github.newsapp.di.ComponentObject
import com.github.newsapp.ui.cicerone.CiceroneScreens
import com.github.newsapp.ui.cicerone.NewsRouter
import com.github.newsapp.ui.view.OnboardingView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

/**
 * Презентер для экрана onboarding
 * @property router
 */
@InjectViewState
class OnboardingPresenter :
    MvpPresenter<OnboardingView>() {

    @Inject
    lateinit var router: NewsRouter

    init {
        injectDependencies()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDependencies()
    }

    private fun injectDependencies() {
        ComponentObject.addOnboardingComponent()
        ComponentObject.onboardingComponent?.inject(this)
    }

    private fun clearDependencies() {
        ComponentObject.clearOnboardingComponent()
    }

    /**
     * Установка текста кнопки
     * @param lastScreen флаг, показывающий, достиг пользователь последнего экрана или нет
     */
    fun checkCurrentScreen(lastScreen: Boolean) {
        val btnText = if (lastScreen) "Начать" else "Пропустить"
        viewState.setButtonText(btnText)
    }

    /**
     * Навигация на главный экран приложения
     */
    fun startUsing() {
        router.navigateToRecordsScreen(CiceroneScreens.mainFragment())
    }
}