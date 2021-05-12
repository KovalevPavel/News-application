package com.github.newsapp.ui.view

import com.github.newsapp.domain.entities.NewsItemExtended
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsDetailsView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun updateNewsID()

    fun bindDetails(newsDetails: NewsItemExtended)
    fun setImageToViewPager(imageID: Int)
}