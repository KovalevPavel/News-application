package com.github.newsapp.di.qualifiers.api

import javax.inject.Qualifier

/**
 * Квалификатор, указывающий на необходимость подключения реального сетевого Api
 */
@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class RemoteApi
