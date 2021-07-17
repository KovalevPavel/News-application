package com.github.newsapp.domain.entities

/**
 * Класс заголовка экрана новостей
 */
class HeaderItem : DisplayInRecycleItem {
    override fun funCompare(other: DisplayInRecycleItem?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HeaderItem

        return true
    }
}
