package com.neocaptainnemo.cv.core.di

import com.neocaptainnemo.cv.core.analytics.AnalyticsService
import com.neocaptainnemo.cv.core.analytics.AnalyticsServiceImpl
import com.neocaptainnemo.cv.core.data.DataService
import com.neocaptainnemo.cv.core.data.DataServiceImpl
import com.neocaptainnemo.cv.core.locale.LocaleService
import com.neocaptainnemo.cv.core.locale.LocaleServiceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val coreModule = module {

    single<DataService> {
        DataServiceImpl(get())
    }
    single<LocaleService> {
        LocaleServiceImpl(get())
    }
    single<AnalyticsService> {
        AnalyticsServiceImpl(get())
    }
}
