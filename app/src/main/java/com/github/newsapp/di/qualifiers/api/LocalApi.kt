package com.github.newsapp.di.qualifiers.api

import javax.inject.Qualifier

/**
 * Квалификатор, указывающий на необходимость подключения фейкового сетевого Api (для тестов)
 */
@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class LocalApi
