package com.neocaptainnemo.cv

import android.app.Activity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection


fun androidx.fragment.app.Fragment.daggerInject() = AndroidSupportInjection.inject(this)

fun Activity.daggerInject() = AndroidInjection.inject(this)