package com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates

import android.content.Context
import android.view.View
import com.github.newsapp.R
import com.github.newsapp.databinding.FragmentNewsDetailsBinding
import com.github.newsapp.util.getColor

class StateManyImages (
    override val binder: FragmentNewsDetailsBinding,
    context: Context
) : DetailsNewsState(binder) {
    override val navigationButtonColor = getColor(context, R.color.white)
    override fun prepareViewPager() {
        binder.apply {
            viewStub.visibility = View.GONE
            viewPager.visibility = View.VISIBLE
            dotsIndicator.visibility = View.VISIBLE
        }
    }
}