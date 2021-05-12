package com.github.newsapp.ui.fragments.viewpagerFullScreen

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.github.newsapp.databinding.FragmentViewpagerFullscreenBinding
import com.github.newsapp.domain.entities.ImageToLoad
import com.github.newsapp.presenters.ViewPagerFullScreenPresenter
import com.github.newsapp.ui.view.ViewPagerFullScreen
import com.github.newsapp.util.FragmentViewBinding
import moxy.presenter.InjectPresenter

class ViewPagerFullscreenFragment :
    FragmentViewBinding<FragmentViewpagerFullscreenBinding>(FragmentViewpagerFullscreenBinding::inflate),
    ViewPagerFullScreen {

    @InjectPresenter
    lateinit var viewPagerFullScreenPresenter: ViewPagerFullScreenPresenter

//    позиция текущего изображения
    private var currentImage = 0
//    адаптер
    private var fullScreenAdapter: ViewPagerFullScreenAdapter? = null
//    слушатель события скролла
    private val onViewPagerChangeImage = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewPagerFullScreenPresenter.setNewImageID(position)
            currentImage = position
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAdapter = ViewPagerFullScreenAdapter {
            viewPagerFullScreenPresenter.navigateBack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder.viewPagerFullscreen.adapter = fullScreenAdapter
        viewPagerFullScreenPresenter.loadInitialInformation()
        binder.viewPagerFullscreen.registerOnPageChangeCallback(onViewPagerChangeImage)
    }

//    загрузка начальной информации
    override fun loadInitialInfo(imageID: Int, imageList: List<ImageToLoad>) {
        fullScreenAdapter?.let {
            it.updateImageList(imageList)
            it.notifyDataSetChanged()
        }
        binder.viewPagerFullscreen.setCurrentItem(imageID, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPagerFullScreenPresenter.setNewImageID(currentImage)
        binder.viewPagerFullscreen.unregisterOnPageChangeCallback(onViewPagerChangeImage)
        fullScreenAdapter = null
    }
}