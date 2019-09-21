package com.neocaptainnemo.cv.app

import androidx.multidex.MultiDexApplication
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.*
import com.neocaptainnemo.cv.ui.common.CommonViewModel
import com.neocaptainnemo.cv.ui.contacts.ContactsViewModel
import com.neocaptainnemo.cv.ui.projects.ProjectDetailsViewModel
import com.neocaptainnemo.cv.ui.projects.ProjectsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
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
}

val vmModule = module {

    viewModel {
        CommonViewModel(get())
    }

    viewModel {
        ContactsViewModel(get())
    }

    viewModel {
        ProjectsViewModel(get())
    }

    viewModel { (project: Project) ->
        ProjectDetailsViewModel(androidApplication(), project)
    }

}


open class App : MultiDexApplication() {

    open val modules: List<Module>
        get() = listOf(appModule, vmModule)


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(modules)
        }
    }
}



