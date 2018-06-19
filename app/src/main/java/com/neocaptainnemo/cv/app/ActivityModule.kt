package com.neocaptainnemo.cv.app

import com.neocaptainnemo.cv.MainActivity
import com.neocaptainnemo.cv.ui.projects.ProjectDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun contributeProjectDetailsActivity(): ProjectDetailsActivity

}