package com.github.newsapp.domain.usecases.viewpagerInteraction

import com.github.newsapp.data.entities.ImageToLoad
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class ViewPagerInteraction @Inject constructor() {

    companion object {
        const val SCROLL_PERIOD = 2L
    }

    var currentScreen = -1
    var imageList = emptyList<ImageToLoad>()
    var disposableCounter: Disposable? = null
}