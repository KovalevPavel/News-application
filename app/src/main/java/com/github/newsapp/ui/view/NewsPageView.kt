package com.github.newsapp.ui.view

import android.view.View
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface NewsPageView : MvpView {
    /**
     * Показ начальной анимации при переходе с onboarding-фрагмента
     */
    fun showRevealAnim(view: View)

    /**
     * Обновление списка записей
     * @param recList список записей для отображения
     */
    @StateStrategyType(SingleStateStrategy::class)
    fun updateNewsList(recList: List<DisplayInRecycleItem>)

    /**
     * Переключение видимости кнопки "вверх"
     * @param toggle флаг видимости кнопки
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun toggleUpButton(toggle: Boolean)

    /**
     * Переключение видимости ProgressBar при загрузке очередной страницы новостей
     * @param toggle флаг видимости ProgressBar
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun togglePageLoading(toggle: Boolean)

    /**
     * Переключение видимости ProgressBar при загрузке списка записей
     * @param toggle флаг видимости ProgressBar
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun toggleRecordsLoading(toggle: Boolean)

    /**
     * Отображение диалога с рейтингом приложения
     * @param toggle флаг видимости диалога
     */
    fun toggleRatingDialog(toggle: Boolean)
}