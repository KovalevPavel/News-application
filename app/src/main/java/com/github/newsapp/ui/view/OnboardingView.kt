package com.github.newsapp.ui.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface OnboardingView : MvpView {
    /**
     * Установка определенной страницы во viewPager
     * @param page позиция страницы в массиве
     */
    fun setPageToViewPager(page: Int)

    /**
     * Установка текста кнопки
     * @param text текст
     */
    fun setButtonText(text: String)
}