package com.github.newsapp.ui.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface OnboardingView : MvpView {
    fun setPageToViewPager(page: Int)
    fun setButtonText(text: String)
}