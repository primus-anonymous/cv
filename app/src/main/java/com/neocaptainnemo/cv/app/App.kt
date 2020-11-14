package com.neocaptainnemo.cv.app

import androidx.multidex.MultiDexApplication
import com.neocaptainnemo.cv.core.di.coreModule
import com.neocaptainnemo.cv.ui.common.CommonViewModel
import com.neocaptainnemo.cv.ui.contacts.ContactsViewModel
import com.neocaptainnemo.cv.ui.projects.ProjectDetailsViewModel
import com.neocaptainnemo.cv.ui.projects.ProjectsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
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

    viewModel { (project: com.neocaptainnemo.cv.core.model.Project) ->
        ProjectDetailsViewModel(androidApplication(),
                                project)
    }

}


@FlowPreview
@ExperimentalCoroutinesApi
open class App : MultiDexApplication() {

    open val modules: List<Module>
        get() = listOf(coreModule,
                       vmModule)


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(modules)
        }
    }
}



