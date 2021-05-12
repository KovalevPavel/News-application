package com.github.newsapp.ui.fragments.newsDetailsFragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.github.newsapp.NewsApplication
import com.github.newsapp.R
import com.github.newsapp.databinding.FragmentNewsDetailsBinding
import com.github.newsapp.domain.entities.NewsItemExtended
import com.github.newsapp.domain.usecases.loadingnews.NewsTypes
import com.github.newsapp.presenters.NewsDetailsPresenter
import com.github.newsapp.ui.fragments.newsDetailsFragment.adapters.ViewPagerAdapter
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.DetailsNewsState
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.StateManyImages
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.StateNoImages
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.StateOneImage
import com.github.newsapp.ui.view.NewsDetailsView
import com.github.newsapp.util.FragmentViewBinding
import com.github.newsapp.util.loggingDebug
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.*

class NewsDetailsFragment :
    FragmentViewBinding<FragmentNewsDetailsBinding>(FragmentNewsDetailsBinding::inflate),
    NewsDetailsView {

    companion object {
        private const val NEWS_ID = "newsID"
        fun newInstance(newsID: Long): NewsDetailsFragment {
            val args = Bundle()
            args.putLong(NEWS_ID, newsID)
            return NewsDetailsFragment().also {
                it.arguments = args
            }
        }
    }

    @InjectPresenter
    lateinit var detailsPresenter: NewsDetailsPresenter

    @ProvidePresenter
    fun provideDetailsPresenter(): NewsDetailsPresenter {
        return NewsDetailsPresenter(requireActivity(), NewsApplication.instance.router)
    }

//    переменная "состояние фрагмента"
    private lateinit var fragmentState: DetailsNewsState
//    адаптер
    private lateinit var viewPagerAdapter: ViewPagerAdapter
//    слушатель события скролла
    private val onViewPagerItemChange = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            detailsPresenter.onScrollViewPager(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsApplication.newsApplicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        viewPagerAdapter = ViewPagerAdapter {
            detailsPresenter.openFullScreenView()
        }
        detailsPresenter.updateNewsID(requireArguments().getLong(NEWS_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder.viewPager.adapter = viewPagerAdapter
        binder.toolbar.setNavigationOnClickListener {
            detailsPresenter.navigateBack()
        }
        binder.viewPager.registerOnPageChangeCallback(onViewPagerItemChange)
    }

    override fun onStart() {
        super.onStart()
        detailsPresenter.updatePreviewImage()
    }

//    заносим данные в интерфейс
    override fun bindDetails(newsDetails: NewsItemExtended) {
        binder.apply {
            textNewsHeader.text = newsDetails.title
            textNewsDescription.text = newsDetails.description
            textPublishedAt.text = newsDetails.publishedAt.toString()
            setState(newsDetails)
            setupShareButton(newsDetails)
            fragmentState.prepareFragment()
            newsDetails.images?.let {
                viewPagerAdapter.updateImageList(it)
                viewPagerAdapter.notifyDataSetChanged()
            }
            dotsIndicator.setViewPager2(binder.viewPager)
        }
    }

//    настройка кнопки "поделиться"
    private fun setupShareButton(details: NewsItemExtended) {
        val gotShareText = details.shareText !== null
        val shareTextRes = when (details.type) {
            NewsTypes.NEWS -> R.string.btnShare_news
            NewsTypes.ALERT -> R.string.btnShare_alert
        }
        binder.btnShare.apply {
            visibility = if (gotShareText) View.VISIBLE else View.GONE
            setText(shareTextRes)
            setOnClickListener {
                detailsPresenter.shareNews()
            }
        }
    }

//    установка состояния фрагмента
    private fun setState(details: NewsItemExtended) {
        fragmentState = when (details.images?.size) {
            null -> StateNoImages(binder, requireActivity() as AppCompatActivity)
            1 -> StateOneImage(binder, requireActivity() as AppCompatActivity)
            else -> StateManyImages(binder, requireActivity() as AppCompatActivity)
        }
    }

//    передача id новости в презентер для последующей загрузки
    override fun updateNewsID() {
        detailsPresenter.updateNewsID(requireArguments().getLong(NEWS_ID))
    }

//    установка нового изображения во viewpager
    override fun setImageToViewPager(imageID: Int) {
        binder.viewPager.setCurrentItem(imageID, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binder.viewPager.unregisterOnPageChangeCallback(onViewPagerItemChange)
    }
}