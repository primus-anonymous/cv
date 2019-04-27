package com.neocaptainnemo.cv.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neocaptainnemo.cv.ui.common.CommonViewModel
import com.neocaptainnemo.cv.ui.contacts.ContactsViewModel
import com.neocaptainnemo.cv.ui.projects.ProjectsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Binds
    @Singleton
    fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProjectsViewModel::class)
    fun bindProjectsViewModel(vm: ProjectsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommonViewModel::class)
    fun bindCommonViewModel(vm: CommonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    fun bindContactsViewModel(vm: ContactsViewModel): ViewModel

}