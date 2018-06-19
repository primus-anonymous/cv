package com.neocaptainnemo.cv

import android.app.Activity
import android.support.v4.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection


fun Fragment.daggerInject() = AndroidSupportInjection.inject(this)

fun Activity.daggerInject() = AndroidInjection.inject(this)