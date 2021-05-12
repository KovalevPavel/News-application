package com.github.newsapp.domain.usecases.viewpagerInteraction

import com.github.newsapp.domain.entities.ImageToLoad
import io.reactivex.rxjava3.disposables.Disposable

class ViewPagerInteractionImpl: ViewPagerInteraction() {

    override var currentScreen = -1
    override var imageList = emptyList<ImageToLoad>()
    override var disposableCounter: Disposable? = null
}