package com.github.newsapp.domain.usecases

import com.github.newsapp.data.ExternalConnectionRepository
import com.github.newsapp.di.scopes.NewsScope
import javax.inject.Inject

@NewsScope
class ExternalConnectionsUseCase @Inject constructor(private val extConRepo: ExternalConnectionRepository) {
    fun openGooglePlay() {
        extConRepo.openGooglePlay()
    }
}