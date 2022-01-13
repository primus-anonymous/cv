package com.neocaptainnemo.cv.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.neocaptainnemo.cv.R

enum class Tab(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    CONTACTS(
        "contacts",
        R.string.title_contacts,
        R.drawable.ic_call
    ),
    PROJECTS(
        "projects",
        R.string.project,
        R.drawable.ic_store
    ),
    COMMON(
        "common",
        R.string.action_common,
        R.drawable.ic_common
    )
}
