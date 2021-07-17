package com.github.newsapp.ui.view

import com.github.newsapp.data.entities.RecItemExtended
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsDetailsView : MvpView {
    /**
     * Обновление id записи
     */
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun updateNewsID()

    /**
     * Заполнение полей View полученными данными
     * @param details объект с деталями записи
     */
    fun bindDetails(details: RecItemExtended)

    /**
     * Установка изображения во ViewPager
     * @param imageID id изображения
     */
    fun setImageToViewPager(imageID: Int)
}