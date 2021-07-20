package com.github.newsapp.ui.fragments.onboardingFragment

import androidx.viewpager2.widget.ViewPager2
import com.github.newsapp.databinding.FragmentOnboardingBinding
import com.github.newsapp.ui.presenters.OnboardingPresenter
import com.github.newsapp.ui.view.OnboardingView
import com.github.newsapp.util.FragmentViewBinding
import moxy.presenter.InjectPresenter

/**
 * Фрагмент, отвечающий за отображение экрана onboarding
 */
class OnboardingFragment :
    FragmentViewBinding<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate),
    OnboardingView {
    private val viewPagerAdapter = OnboardingViewPagerAdapter()

    @InjectPresenter
    lateinit var onboardingPresenter: OnboardingPresenter

    override fun onStart() {
        installViewPager()
        installDotsIndicator()
        installButton()
        super.onStart()
    }

    /**
     * Установка viewPager
     */
    private fun installViewPager() {
        binder.onboardingViewPager.adapter = viewPagerAdapter
        binder.onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                настройка текста кнопки в зависимости от текущей страницы
                onboardingPresenter.checkCurrentScreen(position == viewPagerAdapter.itemCount - 1)
            }
        })
    }

    /**
     * Установка индикатора
     */
    private fun installDotsIndicator() {
        binder.dotsOnboarding.setViewPager2(binder.onboardingViewPager)
    }

    /**
     * Установка кнопки перехода
     */
    private fun installButton() {
        binder.btnOnboarding.setOnClickListener {
            onboardingPresenter.startUsing()
        }
    }

    override fun setPageToViewPager(page: Int) {
        binder.onboardingViewPager.setCurrentItem(page, true)
    }

    override fun setButtonText(text: String) {
        binder.btnOnboarding.text = text
    }
}