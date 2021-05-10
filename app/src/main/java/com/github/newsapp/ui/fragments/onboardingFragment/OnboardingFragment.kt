package com.github.newsapp.ui.fragments.onboardingFragment

import androidx.viewpager2.widget.ViewPager2
import com.github.newsapp.databinding.FragmentOnboardingBinding
import com.github.newsapp.presenters.OnboardingPresenter
import com.github.newsapp.ui.view.OnboardingView
import com.github.newsapp.util.FragmentViewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class OnboardingFragment :
    FragmentViewBinding<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate),
    OnboardingView {
    private val viewPagerAdapter = OnboardingViewPagerAdapter()

    @InjectPresenter
    lateinit var onboardingPresenter: OnboardingPresenter

    @ProvidePresenter
    fun providePresenter(): OnboardingPresenter {
        return OnboardingPresenter(requireActivity())
    }

    override fun onStart() {
        binder.onboardingViewPager.adapter = viewPagerAdapter
        binder.dotsOnboarding.setViewPager2(binder.onboardingViewPager)
        binder.onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onboardingPresenter.checkCurrentScreen(position == viewPagerAdapter.itemCount - 1)
            }
        })
        binder.btnOnboarding.setOnClickListener {
            onboardingPresenter.startUsing()
        }
        super.onStart()
    }

    override fun setPageToViewPager(page: Int) {
        binder.onboardingViewPager.setCurrentItem(page, true)
    }

    override fun setButtonText(text: String) {
        binder.btnOnboarding.text = text
    }
}