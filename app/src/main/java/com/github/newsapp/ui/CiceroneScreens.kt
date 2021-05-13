package com.github.newsapp.ui

import com.github.newsapp.presenters.PresenterWithRetry
import com.github.newsapp.ui.fragments.newsDetailsFragment.NewsDetailsFragment
import com.github.newsapp.ui.fragments.newsFragment.NewsFragment
import com.github.newsapp.ui.fragments.retryFragment.FragmentRetry
import com.github.newsapp.ui.fragments.viewpagerFullScreen.ViewPagerFullscreenFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object CiceroneScreens {
    fun mainFragment() = FragmentScreen { NewsFragment() }
    fun retryScreen(parentViewPresenter: PresenterWithRetry) =
        FragmentScreen { FragmentRetry.newInstance(parentViewPresenter) }

    fun newsDetailsScreen(newsID: Long) = FragmentScreen { NewsDetailsFragment.newInstance(newsID) }
    fun fullScreenViewPager() = FragmentScreen { ViewPagerFullscreenFragment() }
}