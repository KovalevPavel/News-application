package com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.viewHolders

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.github.newsapp.NewsApplication
import com.github.newsapp.R
import com.github.newsapp.databinding.ItemNewsHeaderBinding
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.entities.HeaderItem
import com.github.newsapp.domain.usecases.timestamp.TimeOfTheDay

class HeaderItemViewHolder(
    private val binder: ItemNewsHeaderBinding,
    private val parentActivity: Activity
) : AbstractViewHolder(binder) {

    companion object {
        private const val PACKAGE_NAME = "com.spotify.music"
    }

    override fun bind(item: DisplayInRecycleItem) {
        if (item !is HeaderItem) return
        binder.apply {
            textAppHeader.setText(R.string.main_screen_name)
            textGreetings.text = makeGreetings()
            btnPlayMarket.setOnClickListener {
                openPlayStore()
            }
        }
    }

    private val timestampUseCase = NewsApplication.newsApplicationComponent.getTimeStampUseCase()

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

    private fun openPlayStore() {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=${PACKAGE_NAME}")
            )
            parentActivity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=${PACKAGE_NAME}")
            )
            parentActivity.startActivity(intent)
        }
    }
}