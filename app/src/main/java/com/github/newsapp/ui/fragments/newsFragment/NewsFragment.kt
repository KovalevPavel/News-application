package com.github.newsapp.ui.fragments.newsFragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.newsapp.NewsApplication
import com.github.newsapp.databinding.FragmentNewsPageBinding
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.domain.usecases.filesystem.FileSystemUseCase
import com.github.newsapp.presenters.NewsPresenter
import com.github.newsapp.presenters.PresenterWithRetry
import com.github.newsapp.util.startCircularReveal
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.NewsItemsDecorator
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.NewsOnScrollListener
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.adapters.NewsRecyclerAdapter
import com.github.newsapp.ui.fragments.ratingDialogFragment.RatingDialogFragment
import com.github.newsapp.ui.view.NewsPageView
import com.github.newsapp.util.FragmentViewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class NewsFragment : FragmentViewBinding<FragmentNewsPageBinding>(FragmentNewsPageBinding::inflate),
    NewsPageView, PresenterWithRetry {

    private var dialog: RatingDialogFragment? = null
    private var newsAdapter: NewsRecyclerAdapter? = null

    private val firstLaunch: Boolean
        get() = launchNumber == 1
    private val launchNumber: Int
        get() = NewsApplication.currentLaunchNumber

    @InjectPresenter
    lateinit var newsPresenter: NewsPresenter

    @Inject
    lateinit var applicationContext: Context

    @Inject
    lateinit var filesystemUseCase: FileSystemUseCase

    @ProvidePresenter
    fun provideNewsPresenter() =
        NewsPresenter(
            NewsApplication.instance.router
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstLaunch) {
            newsPresenter.revealFragment(view)
        }

        newsAdapter = NewsRecyclerAdapter(
            newsPresenter,
            applicationContext,
            requireActivity()
        ) { newsPosition ->
            newsAdapter?.getNewsID(newsPosition)?.let {
                newsPresenter.navigateToDetails(it)
            }
        }

        binder.recyclerNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            addItemDecoration(NewsItemsDecorator(applicationContext))
            addOnScrollListener(NewsOnScrollListener(newsPresenter))
        }
        binder.swipeLayout.setOnRefreshListener {
            loadNewsList()
        }
        binder.btnUp.setOnClickListener {
            binder.recyclerNews.smoothScrollToPosition(0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsApplication.newsApplicationComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun loadNewsList() {
        newsPresenter.loadNews()
        binder.swipeLayout.isRefreshing = false
    }

    override fun retryLoading() = loadNewsList()

    override fun showRevealAnim(view: View) {
        view.startCircularReveal()
    }

    override fun updateNewsList(newsList: List<DisplayInRecycleItem>) {
        newsAdapter?.items = newsList
    }

    override fun toggleUpButton(toggle: Boolean) {
        binder.newsMotionLayout.apply {
            if (toggle) transitionToEnd()
            else transitionToStart()
        }
    }

    override fun toggleLoading(toggle: Boolean) {
        if (toggle) {
            binder.progressBar.visibility = View.VISIBLE
            val lastIndex = (newsAdapter?.itemCount?.minus(1)) ?: 0
            binder.recyclerNews.layoutManager?.scrollToPosition(lastIndex)
        } else binder.progressBar.visibility = View.GONE
    }

    override fun toggleRatingDialog(toggle: Boolean) {
        if (toggle && launchNumber >= 3 && launchNumber % 3 == 0)
            dialog = RatingDialogFragment().also {
                it.show(childFragmentManager, null)
            }
        else dialog?.dismissAllowingStateLoss()
    }

    override fun translateLaunchNumber() {
        newsPresenter.launchNumber = launchNumber
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsAdapter = null
        dialog?.dismissAllowingStateLoss()
    }
}