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
import com.github.newsapp.startCircularReveal
import com.github.newsapp.ui.ExitWithAnimation
import com.github.newsapp.ui.FragmentWithRetry
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.NewsItemsDecorator
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.NewsOnScrollListener
import com.github.newsapp.ui.fragments.newsFragment.newsRecyclerView.adapters.NewsRecyclerAdapter
import com.github.newsapp.ui.fragments.newsFragment.ratedialog.RateAlertDialog
import com.github.newsapp.ui.view.NewsPageView
import com.github.newsapp.util.FragmentViewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MainFragment : FragmentViewBinding<FragmentNewsPageBinding>(FragmentNewsPageBinding::inflate),
    NewsPageView, FragmentWithRetry, ExitWithAnimation {

    override var firstLaunch: Boolean = false
    private var launchNumber: Int? = null

    companion object {
        private const val LAUNCH_NUMBER = "launch_number"
        fun newInstance(launchNumber: Int): MainFragment {
            val args = Bundle()
            args.putInt(LAUNCH_NUMBER, launchNumber)
            return MainFragment().also {
                it.arguments = args
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstLaunch) {
            view.startCircularReveal()
            firstLaunch = false
        }
    }

    override fun retryLoading() = loadNewsList()

    private var dialog: RateAlertDialog? = null
    private var newsAdapter: NewsRecyclerAdapter? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        //Инъекция зависимостей
        NewsApplication.newsApplicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        launchNumber = requireArguments().getInt(LAUNCH_NUMBER)
        launchNumber?.let {
            firstLaunch = it == 1
            if (it > 2 && (it % 3 == 0))
                newsPresenter.showRatingDialog(firstLaunch)
        }

        launchNumber?.let {

        }
    }

    override fun onStart() {
        super.onStart()
        newsAdapter = NewsRecyclerAdapter(
            this,
            applicationContext
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

    override fun loadNewsList() {
        newsPresenter.loadNews()
        binder.swipeLayout.isRefreshing = false
    }

    override fun updateNewsList(newsList: List<DisplayInRecycleItem>) {
        newsAdapter?.items = newsList
    }

    override fun showUpButton() = binder.newsMotionLayout.transitionToEnd()
    override fun hideUpButton() = binder.newsMotionLayout.transitionToStart()

    override fun showLoading() {
        binder.progressBar.visibility = View.VISIBLE
        val lastIndex = (newsAdapter?.itemCount?.minus(1)) ?: 0
        binder.recyclerNews.layoutManager?.scrollToPosition(lastIndex)
    }

    override fun hideLoading() {
        binder.progressBar.visibility = View.GONE
    }

    override fun showRatingDialog(rating: Float, isFirstLaunch: Boolean) {
        dialog = RateAlertDialog(applicationContext, isFirstLaunch)
//        dialog as AlertDialog
        dialog?.let {
            it.setRating(rating)
            it.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsAdapter = null
//        dialog?.dismiss()
    }
}