package com.github.newsapp.domain.usecases

import com.github.newsapp.data.FileSystemRepository
import javax.inject.Inject

class UserNameUseCase @Inject constructor(private val fileSystemRepo: FileSystemRepository) {

    fun getUserName(): String {
        return fileSystemRepo.getUserName()
    }
    fun setUserName (userName: String) {
        fileSystemRepo.setUserName(userName)
    }
}