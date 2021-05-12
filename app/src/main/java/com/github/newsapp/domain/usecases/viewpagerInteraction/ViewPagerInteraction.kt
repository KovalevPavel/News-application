package com.github.newsapp.domain.usecases.viewpagerInteraction

import com.github.newsapp.domain.entities.ImageToLoad
import io.reactivex.rxjava3.disposables.Disposable

abstract class ViewPagerInteraction {
    companion object {
        const val SCROLL_PERIOD = 2L
    }
    abstract var currentScreen: Int
    abstract var imageList : List<ImageToLoad>
    abstract var disposableCounter: Disposable?
}