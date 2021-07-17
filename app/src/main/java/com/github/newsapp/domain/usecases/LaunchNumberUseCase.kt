package com.github.newsapp.domain.usecases

import com.github.newsapp.data.LaunchNumberRepository
import com.github.newsapp.di.scopes.MainActivityScope
import javax.inject.Inject

@MainActivityScope
class LaunchNumberUseCase @Inject constructor(private val launchNumberRepo: LaunchNumberRepository) {
    suspend fun getLaunchNumber(): Int {
        return launchNumberRepo.getLaunchNumber()
    }

    suspend fun increaseLaunchNumber(oldNumber: Int) {
        launchNumberRepo.increaseLaunchNumber(oldNumber)
    }

    suspend fun setOnboardingNeeded(needed: Boolean) {
        launchNumberRepo.setOnboardingNeeded(needed)
    }

    suspend fun getOnboardingNeeded(): Boolean {
        return launchNumberRepo.getOnboardingNeeded()
    }
}