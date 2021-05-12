package com.github.newsapp.ui.view

import androidx.fragment.app.Fragment
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType (OneExecutionStateStrategy::class)
interface ActivityView: MvpView {
    fun navigateToFragment(fragmentToNavigate: Fragment)
}