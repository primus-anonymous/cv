package com.neocaptainnemo.cv.app

import com.neocaptainnemo.cv.ui.common.CommonFragment
import com.neocaptainnemo.cv.ui.contacts.ContactsFragment
import com.neocaptainnemo.cv.ui.projects.ProjectsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun contributeProjectsFragment(): ProjectsFragment

    @ContributesAndroidInjector
    fun contributeCommonFragment(): CommonFragment

    @ContributesAndroidInjector
    fun contributeContactsFragment(): ContactsFragment
}