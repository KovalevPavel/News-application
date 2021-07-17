package com.github.newsapp.ui.fragments.newsFragment.recyclerView.viewHolders

import com.github.newsapp.R
import com.github.newsapp.databinding.ItemNewsHeaderBinding
import com.github.newsapp.di.ComponentObject
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.HeaderItem
import com.github.newsapp.domain.usecases.ExternalConnectionsUseCase
import com.github.newsapp.domain.usecases.TimestampUseCase
import com.github.newsapp.domain.usecases.UserNameUseCase
import javax.inject.Inject

/**
 * ViewHolder, отвечающий за отображение приветствия
 * @param binder переменная ViewBinding, которая используется для доступа к элементам UI ViewHolder 'а
 *
 * @property extUseCase UseCase, отвечающий за обработку команд, направленных на операции с внешними подключениями
 * @property timestampUseCase UseCase, отвечающий за обработку команд, направленных на операции с датой/временем
 * @property userNameUseCase UseCase, отвечающий за обработку команд, направленных на операции с именем пользователя
 */
class HeaderItemViewHolder(
    private val binder: ItemNewsHeaderBinding
) : AbstractViewHolder(binder) {

    @Inject
    lateinit var extUseCase: ExternalConnectionsUseCase

    @Inject
    lateinit var timestampUseCase: TimestampUseCase

    @Inject
    lateinit var userNameUseCase: UserNameUseCase

    init {
        injectDependencies()
    }

    override fun bind(item: DisplayInRecycleItem) {
        if (item !is HeaderItem) return
        binder.apply {
            textAppHeader.setText(R.string.main_screen_name)
            textGreetings.text = makeGreetings()
            btnPlayMarket.setOnClickListener {
                extUseCase.openGooglePlay()
            }
        }
    }

    //получаем строку приветствия
    private fun makeGreetings(): String {
        return "${timestampUseCase.makeTimeOfTheDayString()}, ${getNameString()}"
    }

    //получаем имя пользователя, либо используем имя по умолчанию
    private fun getNameString(): String {
        return userNameUseCase.getUserName()
    }

    private fun injectDependencies() {
        ComponentObject.apply {
            addNewsComponent()
            newsComponent?.inject(this@HeaderItemViewHolder)
        }
    }
}