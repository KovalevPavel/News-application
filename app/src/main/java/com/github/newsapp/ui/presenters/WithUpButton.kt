package com.github.newsapp.ui.presenters

/**
 *Интерфейс для презентера, к которому прикреплена view с кнопкой навигации "вверх"
 */
interface WithUpButton {
    /**
     * Переключение видимости кнопки "вверх"
     */
    fun toggleUpButton(toggle: Boolean)
}