package com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates

import com.github.newsapp.databinding.FragmentNewsDetailsBinding

/**
 * Класс, описывающий состояние фрагмента с деталями записи
 * @param binder объект [FragmentNewsDetailsBinding]
 * @property navigationButtonColor цвет кнопки навигации в тулбаре
 */
abstract class DetailsNewsState (open val binder: FragmentNewsDetailsBinding) {
    abstract val  navigationButtonColor:  Int

    /**
     * Форматирование viewPager
     */
    abstract fun prepareViewPager()

    /**
     * Форматирование тулбара
     */
    private fun paintNavigationButton() {
        binder.toolbar.navigationIcon?.setTint(navigationButtonColor)
    }

    /**
     * Форматирование фрагмента
     */
    fun prepareFragment() {
        prepareViewPager()
        paintNavigationButton()
    }
}