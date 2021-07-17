package com.github.newsapp.ui.fragments.newsFragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.newsapp.app.NewsApplication
import com.github.newsapp.databinding.FragmentNewsPageBinding
import com.github.newsapp.di.ComponentObject
import com.github.newsapp.domain.entities.DisplayInRecycleItem
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.NewsItemsDecorator
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.NewsOnScrollListener
import com.github.newsapp.ui.fragments.newsFragment.recyclerView.adapters.NewsRecyclerAdapter
import com.github.newsapp.ui.fragments.ratingDialogFragment.RatingDialogFragment
import com.github.newsapp.ui.presenters.NewsPresenter
import com.github.newsapp.ui.presenters.PresenterWithRetry
import com.github.newsapp.ui.view.NewsPageView
import com.github.newsapp.util.FragmentViewBinding
import com.github.newsapp.util.startCircularReveal
import kotlinx.coroutines.launch
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Фрагмент, отвечающий за отображение новостной ленты
 * @property dialog диалог установки рейтинга приложению
 * @property newsAdapter адаптер для Recycler view новостной ленты
 * @property firstLaunch флаг, показывающий, это первый запуск приложения или нет
 * @property launchNumber номер запуска приложения
 * @property newsPresenter презентер для экрана со списком новостей
 * @property newsItemsDecorator декоратор для установки в Recycler view
 */
class NewsFragment : FragmentViewBinding<FragmentNewsPageBinding>(FragmentNewsPageBinding::inflate),
    NewsPageView, PresenterWithRetry {

    private var dialog: RatingDialogFragment? = null
    private var newsAdapter: NewsRecyclerAdapter? = null

    private val firstLaunch: Boolean
        get() = launchNumber == 1
    private val launchNumber: Int
        get() = NewsApplication.currentLaunchNumber

    @Inject
    lateinit var newsItemsDecorator: NewsItemsDecorator

    @InjectPresenter
    lateinit var newsPresenter: NewsPresenter

    @ProvidePresenter
    fun provideNewsPresenter() = NewsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstLaunch) {
            newsPresenter.revealFragment(view)
        }
        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsAdapter = null
        dialog?.dismissAllowingStateLoss()
    }

    private fun loadNewsList() {
        lifecycleScope.launch {
            togglePageLoading(true)
            newsPresenter.loadNews()
            togglePageLoading(false)
        }
    }

    override fun retryLoading() = loadNewsList()

    override fun showRevealAnim(view: View) {
        view.startCircularReveal()
    }

    override fun updateNewsList(recList: List<DisplayInRecycleItem>) {
        lifecycleScope.launch {
            newsAdapter?.items = recList
        }
    }

    override fun toggleUpButton(toggle: Boolean) {
        binder.newsMotionLayout.apply {
            if (toggle) transitionToEnd()
            else transitionToStart()
        }
    }

    override fun togglePageLoading(toggle: Boolean) {
//        if (toggle) {
//            binder.progressBar.visibility = View.VISIBLE
//            val lastIndex = (newsAdapter?.itemCount?.minus(1)) ?: 0
//            binder.recyclerNews.layoutManager?.scrollToPosition(lastIndex)
//        } else binder.progressBar.visibility = View.GONE
            binder.swipeLayout.isRefreshing = toggle
    }

    override fun toggleRecordsLoading(toggle: Boolean) {
            binder.progressBar.visibility =
                if (toggle) View.VISIBLE else View.GONE
    }

    override fun toggleRatingDialog(toggle: Boolean) {
        if (toggle && launchNumber >= 3 && launchNumber % 3 == 0)
            dialog = RatingDialogFragment().also {
                it.show(childFragmentManager, null)
            }
        else dialog?.dismissAllowingStateLoss()
    }

    private fun injectDependencies() {
        ComponentObject.apply {
            addNewsComponent()
            newsComponent?.inject(this@NewsFragment)
        }
    }

    private fun initUI() {
        newsAdapter = NewsRecyclerAdapter(
            newsPresenter
        )
        { newsPosition: Int ->
            newsAdapter?.getNewsID(newsPosition)?.let {
                newsPresenter.navigateToDetails(it)
            }
        }

        binder.recyclerNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            addItemDecoration(newsItemsDecorator)
            addOnScrollListener(NewsOnScrollListener(newsPresenter))
        }
        binder.swipeLayout.setOnRefreshListener {
            loadNewsList()
        }
        binder.btnUp.setOnClickListener {
            binder.recyclerNews.smoothScrollToPosition(0)
        }
    }
}