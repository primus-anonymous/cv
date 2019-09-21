package com.neocaptainnemo.cv.ui.contacts

import com.neocaptainnemo.cv.ui.adapter.AdapterItem

data class ContactSection(
        val type: ContactType,
        val title: Int,
        val subTitle: Int,
        val img: Int,
        val value: String
) : AdapterItem {

    override val uniqueTag: String = "ContactSection${type.name}$title"
}
