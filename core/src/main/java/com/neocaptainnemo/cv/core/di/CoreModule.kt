package com.neocaptainnemo.cv.core.di

import com.neocaptainnemo.cv.core.analytics.AnalyticsService
import com.neocaptainnemo.cv.core.analytics.AnalyticsServiceImpl
import com.neocaptainnemo.cv.core.data.CvRepository
import com.neocaptainnemo.cv.core.data.CvRepositoryImpl
import com.neocaptainnemo.cv.core.locale.LocaleService
import com.neocaptainnemo.cv.core.locale.LocaleServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    @Singleton
    internal abstract fun bindAnalyticsService(
        analyticsServiceImpl: AnalyticsServiceImpl
    ): AnalyticsService

    @Binds
    @Singleton
    internal abstract fun bindDataService(
        dataServiceImpl: CvRepositoryImpl
    ): CvRepository

    @Binds
    @Singleton
    internal abstract fun bindLocaleService(
        localeServiceImpl: LocaleServiceImpl
    ): LocaleService

}
