package com.neocaptainnemo.cv.model

import com.google.firebase.database.PropertyName
import com.neocaptainnemo.cv.ui.adapter.AdapterItem


data class CommonSection(

        @set: PropertyName("title_key")
        @get: PropertyName("title_key")
        var title: String? = null,

        @set: PropertyName("description_key")
        @get: PropertyName("description_key")
        var description: String? = null
) : AdapterItem {
    override val uniqueTag: String = "CommonSection$title"
}
