package com.github.newsapp.domain.usecases

import com.github.newsapp.data.FileSystemRepository
import javax.inject.Inject

class RatingUseCase @Inject constructor(private val fileSystemRepo: FileSystemRepository) {
    fun getRating(): Float {
        return fileSystemRepo.getRating()
    }
    fun setRating (rating: Float) {
        fileSystemRepo.setRating(rating)
    }
}