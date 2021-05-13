package com.github.newsapp.ui.view

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface RateDialogView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun dismissDialog()
}