package com.github.newsapp.di.qualifiers.packageName

import javax.inject.Qualifier

/**
 * Квалификатор, указывающий на необходимость передачи фейкового имени пакета приложения
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FakePackageName
