package com.github.newsapp.ui.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType (OneExecutionStateStrategy::class)
interface RetryScreenView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateBack()
}