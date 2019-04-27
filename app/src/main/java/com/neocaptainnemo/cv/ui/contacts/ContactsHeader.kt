package com.neocaptainnemo.cv.ui.contacts

import com.neocaptainnemo.cv.ui.adapter.AdapterItem

data class ContactsHeader(val image: String,
                          val name: String,
                          val profession: String) : AdapterItem {

    override fun uniqueTag(): String = "ContactsHeader"
}