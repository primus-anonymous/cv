package com.neocaptainnemo.cv

import com.neocaptainnemo.cv.app.App
import com.neocaptainnemo.cv.app.vmModule
import com.neocaptainnemo.cv.services.IAnalyticsService
import com.neocaptainnemo.cv.services.IDataService
import com.neocaptainnemo.cv.services.ILocaleService
import org.koin.android.ext.android.inject
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mockito.mock

private val mockAppModule = module {

    single<IDataService> {
        mock(IDataService::class.java)
    }
    single<ILocaleService> {
        mock(ILocaleService::class.java)
    }
    single<IAnalyticsService> {
        mock(IAnalyticsService::class.java)
    }
}


class MockApp : App() {

    val mockDataService: IDataService by inject()

    val mockLocaleService: ILocaleService by inject()

    val mockAnalyticService: IAnalyticsService by inject()


    override val modules: List<Module>
        get() = listOf(mockAppModule, vmModule)

}

