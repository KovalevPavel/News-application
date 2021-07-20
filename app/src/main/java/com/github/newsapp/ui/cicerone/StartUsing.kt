package com.github.newsapp.ui.cicerone

import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Screen

/**
 * Специальная команда для circular reveal анимации
 */
data class StartUsing(val screen: Screen) : Command
