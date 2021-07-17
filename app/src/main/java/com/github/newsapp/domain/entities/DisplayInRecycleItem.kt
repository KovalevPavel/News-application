package com.github.newsapp.domain.entities

/**
 * Интерфейс, описывающий объекты, находящиеся в Recycler view списка новостей
 */
interface DisplayInRecycleItem {
    /**
     * аналог функции equals() для data-классов
     */
    fun funCompare(other: DisplayInRecycleItem?): Boolean
}