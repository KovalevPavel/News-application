package com.github.newsapp.ui.presenters

/**
 * Интерфейс для презентера, который прикреплен к view, с которой возможна навигация на экран "повторить попытку соединения"
 */
interface PresenterWithRetry {
    /**
     * Инициализация повторной загрузки
     */
    fun retryLoading()
}