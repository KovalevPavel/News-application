package com.github.newsapp.ui.cicerone

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

/**
 * Имплементация [Router] с дополнительной командой навигации для circular reveal анимации
 */
class NewsRouter @Inject constructor(): Router() {
    fun navigateToRecordsScreen(screen: FragmentScreen) {
        executeCommands(StartUsing(screen))
    }
}