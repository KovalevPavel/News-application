package com.github.newsapp.domain.usecases.filesystem

interface FileSystemUseCase {
    fun getLaunchNumber(): Int
    fun increaseLaunchNumber(oldNumber: Int)
    fun getUserName(): String
    fun setUserName (userName: String)
    fun getRating(): Float
    fun setRating (rating: Float)
    fun setOnboardingNeeded (needed: Boolean)
    fun getOnboardingNeeded(): Boolean
}