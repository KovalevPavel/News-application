package com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders

import com.github.newsapp.R
import com.github.newsapp.databinding.ItemNewsHeaderBinding
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.HeaderItem
import com.github.newsapp.domain.usecases.timestamp.TimeOfTheDay

class HeaderItemViewHolder(
    private val binder: ItemNewsHeaderBinding,
    onClickListener: (Int) -> Unit
) : AbstractViewHolder(binder, null, onClickListener) {

    override fun bind(item: DisplayInRecycleItem) {
        if (item !is HeaderItem) return
        binder.apply {
            textAppHeader.setText(R.string.main_screen_name)
            textGreetings.text = makeGreetings()
        }
    }

    //получаем строку приветствия
    private fun makeGreetings(): String {
        return "${timestampUseCase.makeTimeOfTheDayString()}, ${getNameString()}"
    }

    //получаем приветствие в зависимости от времени суток
    private fun getTimeOfTheDayString(): Int {
        val currentTime = timestampUseCase.getCurrentTime().toString()
        return TimeOfTheDay.valueOf(currentTime).stringRes
    }

    //получаем имя пользователя, либо используем имя по умолчанию
    private fun getNameString(): String {
        return fileSystemUseCase.getUserName()
    }
}