package com.github.newsapp.presenters

import com.github.newsapp.data.remote.NetworkService
import com.github.newsapp.domain.entities.NewsItemExtended
import com.github.newsapp.ui.view.NewsDetailsView
import com.github.newsapp.util.loggingDebug
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class NewsDetailsPresenter(
    private val networkRepository: NetworkService,
    private val router: Router
) : MvpPresenter<NewsDetailsView>() {

    lateinit var currentNewsDetails: NewsItemExtended
        private set

    private var newsID: Long? = null

    private fun loadDetails() {
        newsID?.let {newsID ->
            networkRepository.getNewsDetails(newsID,
                {
                    currentNewsDetails = it
                    viewState.bindDetails()
                }, {
                    loggingDebug("error generating: $it")
                })
        }
    }

    override fun onFirstViewAttach() {
        loggingDebug("attach")
        super.onFirstViewAttach()
        viewState.updateNewsID()
        loadDetails()
        loggingDebug("current: ")
    }

    fun updateNewsID(updatedID: Long) {
        newsID = updatedID
    }

    fun navigateBack() {
        router.exit()
    }
}