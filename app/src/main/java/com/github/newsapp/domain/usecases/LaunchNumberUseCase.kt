package com.github.newsapp.domain.usecases

import com.github.newsapp.data.LaunchNumberRepository
import javax.inject.Inject

class LaunchNumberUseCase @Inject constructor(private val launchNumberRepo: LaunchNumberRepository) {
    suspend fun getLaunchNumber(): Int {
        return launchNumberRepo.getLaunchNumber()
    }

    suspend fun increaseLaunchNumber() {
        launchNumberRepo.increaseLaunchNumber()
    }
}