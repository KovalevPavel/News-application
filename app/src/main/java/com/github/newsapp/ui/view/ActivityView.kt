package com.github.newsapp.ui.view

import androidx.fragment.app.Fragment
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType (SingleStateStrategy::class)
interface ActivityView: MvpView {
    fun navigateToFragment(fragmentToNavigate: Fragment)
}