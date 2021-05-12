package com.github.newsapp.ui.view

import com.github.newsapp.domain.entities.ImageToLoad
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType (OneExecutionStateStrategy::class)
interface ViewPagerFullScreen: MvpView {
    fun loadInitialInfo (imageID: Int, imageList: List<ImageToLoad>)
}