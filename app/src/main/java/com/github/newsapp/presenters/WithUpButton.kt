package com.github.newsapp.presenters

/*
Интерфейс для презентера, к которому прикреплена view с кнопкой навигации "вверх"
 */
interface WithUpButton {
    fun toggleUpButton(toggle: Boolean)
}