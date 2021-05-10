package com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates

import com.github.newsapp.databinding.FragmentNewsDetailsBinding

abstract class DetailsNewsState (open val binder: FragmentNewsDetailsBinding) {
    abstract val  navigationButtonColor:  Int
    abstract fun prepareViewPager()

    private fun colorNavigationButton() {
        binder.toolbar.navigationIcon?.setTint(navigationButtonColor)
    }

    fun prepareFragment() {
        prepareViewPager()
        colorNavigationButton()
    }
}