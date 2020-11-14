package com.neocaptainnemo.cv.ui.contacts

import androidx.annotation.DrawableRes


data class ContactSection(
        val type: ContactType,
        val title: Int,
        val subTitle: Int,
        @DrawableRes val img: Int,
        val value: String,
)
