package com.github.newsapp.di.qualifiers.packageName

import javax.inject.Qualifier

/**
 * Квалификатор, указывающий на необходимость передачи реального имени пакета приложения
 */
@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class RealPackageName
