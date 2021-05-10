package com.github.newsapp.ui.fragments.newsDetailsFragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import com.github.newsapp.NewsApplication
import com.github.newsapp.R
import com.github.newsapp.data.remote.NetworkService
import com.github.newsapp.databinding.FragmentNewsDetailsBinding
import com.github.newsapp.domain.entities.NewsItemExtended
import com.github.newsapp.presenters.NewsDetailsPresenter
import com.github.newsapp.ui.fragments.newsDetailsFragment.adapters.ViewPagerAdapter
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.DetailsNewsState
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.StateManyImages
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.StateNoImages
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.StateOneImage
import com.github.newsapp.ui.view.NewsDetailsView
import com.github.newsapp.util.FragmentViewBinding
import com.github.newsapp.util.autoscroll
import com.github.newsapp.util.loggingDebug
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

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

    private lateinit var fragmentState: DetailsNewsState
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    @Inject
    lateinit var networkRepository: NetworkService

    @InjectPresenter
    lateinit var detailsPresenter: NewsDetailsPresenter

    @ProvidePresenter
    fun provideDetailsPresenter(): NewsDetailsPresenter {
        return NewsDetailsPresenter(networkRepository, NewsApplication.instance.router)
    }

    private val runnable: Runnable? = Runnable {
        var nextPage = binder.viewPager.currentItem + 1
        if (nextPage == binder.viewPager.adapter?.itemCount)
            nextPage = 0
        binder.viewPager.currentItem = nextPage
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsApplication.newsApplicationComponent.inject(this)
        viewPagerAdapter = ViewPagerAdapter {
            CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    loggingDebug("hold")
                    delay(1000)
                }
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        binder.viewPager.adapter = viewPagerAdapter
        binder.toolbar.setNavigationOnClickListener {
            detailsPresenter.navigateBack()
        }
        binder.viewPager.autoscroll(lifecycleScope, 1500)
    }

    override fun bindDetails() {
        val details = detailsPresenter.currentNewsDetails
        binder.apply {
            textNewsHeader.text = details.title
            textNewsDescription.text = details.description
            textPublishedAt.text = details.publishedAt.toString()
            dotsIndicator.setViewPager2(binder.viewPager)
            setState(details)
            fragmentState.prepareFragment()
            details.images?.let {
                viewPagerAdapter.updateImageList(it)
                viewPagerAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setState(details: NewsItemExtended) {
        fragmentState = when (details.images?.size) {
            null -> StateNoImages(binder, requireActivity() as AppCompatActivity)
            1 -> StateOneImage(binder, requireActivity() as AppCompatActivity)
            else -> StateManyImages(binder, requireActivity() as AppCompatActivity)
        }
    }

    override fun updateNewsID() {
        detailsPresenter.updateNewsID(requireArguments().getLong(NEWS_ID))
    }

    private fun viewPagerFullScreen() {
        binder.viewPager.apply {
            val constraintSet = ConstraintSet()
            constraintSet.clone(binder.viewPagerLayout)
            constraintSet.apply {
                clear(R.id.viewPager)
                connect(
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    R.id.viewPager,
                    ConstraintSet.START
                )
                connect(
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END,
                    R.id.viewPager,
                    ConstraintSet.END
                )
                connect(
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP,
                    R.id.viewPager,
                    ConstraintSet.TOP
                )
                connect(
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    R.id.viewPager,
                    ConstraintSet.BOTTOM
                )
                constrainWidth(R.id.viewPager, ConstraintSet.MATCH_CONSTRAINT)
                constrainHeight(R.id.viewPager, ConstraintSet.MATCH_CONSTRAINT)
            }
        }

    }
}