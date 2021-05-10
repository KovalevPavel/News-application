package com.github.newsapp.ui

import com.github.newsapp.ui.fragments.newsDetailsFragment.NewsDetailsFragment
import com.github.newsapp.ui.fragments.newsFragment.MainFragment
import com.github.newsapp.ui.fragments.noNetworkFragment.FragmentRetry
import com.github.newsapp.ui.fragments.onboardingFragment.OnboardingFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object CiceroneScreens {
    fun onboardFragment() = FragmentScreen { OnboardingFragment() }
    fun mainFragment(launchNumber: Int) = FragmentScreen { MainFragment.newInstance(launchNumber) }
    fun retryScreen() = FragmentScreen { FragmentRetry() }
    fun newsDetailsScreen(newsID: Long) = FragmentScreen { NewsDetailsFragment.newInstance(newsID) }
}