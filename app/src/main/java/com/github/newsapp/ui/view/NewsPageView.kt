package com.github.newsapp.ui.view

import android.view.View
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface NewsPageView : MvpView {
    fun showRevealAnim(view: View)
    @StateStrategyType(SingleStateStrategy::class)
    fun updateNewsList(newsList: List<DisplayInRecycleItem>)
    fun loadNewsList()
    @StateStrategyType (AddToEndSingleStrategy::class)
    fun toggleUpButton(toggle: Boolean)
    @StateStrategyType (AddToEndSingleStrategy::class)
    fun toggleLoading(toggle: Boolean)
    fun toggleRatingDialog(toggle: Boolean)
    fun translateLaunchNumber()
}