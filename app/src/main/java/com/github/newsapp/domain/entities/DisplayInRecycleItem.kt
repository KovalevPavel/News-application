package com.github.newsapp.domain.entities

/*
fun funCompare(other: DisplayInRecycleItem?): Boolean - аналог функции equals() для data-классов
Реализация - переписанная функция equals()
 */

interface DisplayInRecycleItem {
    fun funCompare(other: DisplayInRecycleItem?): Boolean
}