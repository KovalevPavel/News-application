package com.github.newsapp.presenters

/*
Интерфейс для презентера, который прикреплен к view,
с которой возможна навигация на экран "повторить попытку соединения"
 */
interface PresenterWithRetry {
    fun retryLoading()
}