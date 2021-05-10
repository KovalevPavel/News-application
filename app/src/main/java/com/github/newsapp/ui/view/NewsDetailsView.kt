package com.github.newsapp.ui.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsDetailsView : MvpView {
    fun bindDetails()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun updateNewsID()
}