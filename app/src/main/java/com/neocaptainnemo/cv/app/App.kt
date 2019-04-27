package com.neocaptainnemo.cv.app

import android.app.Application
import com.neocaptainnemo.cv.services.*
import com.neocaptainnemo.cv.ui.common.CommonViewModel
import com.neocaptainnemo.cv.ui.contacts.ContactsViewModel
import com.neocaptainnemo.cv.ui.projects.ProjectsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val appModule = module {

    single<IDataService> {
        DataService(get())
    }
    single<ILocaleService> {
        LocaleService(get())
    }
    single<IAnalyticsService> {
        AnalyticsService(get())
    }

    viewModel {
        CommonViewModel(get())
    }

    viewModel {
        ContactsViewModel(get())
    }

    viewModel {
        ProjectsViewModel(get())
    }
}


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}



