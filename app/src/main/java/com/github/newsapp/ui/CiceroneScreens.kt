package com.github.newsapp.ui

import com.github.newsapp.ui.fragments.newsDetailsFragment.NewsDetailsFragment
import com.github.newsapp.ui.fragments.newsFragment.MainFragment
import com.github.newsapp.ui.fragments.noNetworkFragment.FragmentRetry
import com.github.newsapp.ui.fragments.onboardingFragment.OnboardingFragment
import com.github.newsapp.ui.fragments.viewpagerFullScreen.ViewPagerFullscreenFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object CiceroneScreens {
    fun mainFragment() = FragmentScreen { MainFragment() }
    fun retryScreen() = FragmentScreen { FragmentRetry() }
    fun newsDetailsScreen(newsID: Long) = FragmentScreen { NewsDetailsFragment.newInstance(newsID) }
    fun fullScreenViewPager() = FragmentScreen { ViewPagerFullscreenFragment() }
}