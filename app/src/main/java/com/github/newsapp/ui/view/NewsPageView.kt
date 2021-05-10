package com.github.newsapp.ui.view

import com.github.newsapp.domain.entities.DisplayInRecycleItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface NewsPageView : MvpView {
    fun loadNewsList()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateNewsList(newsList: List<DisplayInRecycleItem>)
    fun showUpButton()
    fun hideUpButton()
    fun showLoading()
    fun hideLoading()
    fun showRatingDialog(rating: Float, isFirstLaunch: Boolean)
}