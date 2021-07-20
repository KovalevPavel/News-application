package com.github.newsapp.ui.view

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Navigator
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType (OneExecutionStateStrategy::class)
interface ActivityView: MvpView {
    fun navigateToFragment(fragmentToNavigate: Fragment)
}