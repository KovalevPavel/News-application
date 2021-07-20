package com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates

import android.content.Context
import android.view.View
import com.github.newsapp.R
import com.github.newsapp.databinding.FragmentNewsDetailsBinding
import com.github.newsapp.util.getColor

/**
 * Имплементация [DetailsNewsState] для случая, когда с сервера не приходят изображения к записи
 */
class StateNoImages(
    override val binder: FragmentNewsDetailsBinding,
    context: Context
) : DetailsNewsState(binder) {
    override val navigationButtonColor = getColor(context, R.color.black)
    override fun prepareViewPager() {
        binder.apply {
            viewStub.visibility = View.VISIBLE
            viewPager.visibility = View.GONE
            dotsIndicator.visibility = View.GONE
        }
    }
}