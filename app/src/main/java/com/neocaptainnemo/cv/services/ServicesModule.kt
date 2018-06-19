package com.neocaptainnemo.cv.services

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ServicesModule {

    @Binds
    @Singleton
    fun bindsDataService(service: DataService): IDataService

    @Binds
    @Singleton
    fun bindsLocaleService(service: LocaleService): ILocaleService

    @Binds
    @Singleton
    fun bindsAnalyticsService(service: AnalyticsService): IAnalyticsService

}