package com.github.newsapp.domain.usecases

import com.github.newsapp.data.RatingRepository
import com.github.newsapp.di.scopes.RatingScope
import javax.inject.Inject

@RatingScope
class RatingUseCase @Inject constructor(private val ratingRepo: RatingRepository) {
    suspend fun getRating(): Float {
        return ratingRepo.getRating()
    }
    suspend fun setRating (rating: Float) {
        ratingRepo.setRating(rating)
    }
}