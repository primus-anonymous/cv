package com.neocaptainnemo.cv.core.model

import com.google.firebase.database.PropertyName


data class CommonSection(

        @set: PropertyName("title_key")
        @get: PropertyName("title_key")
        var title: String? = null,

        @set: PropertyName("description_key")
        @get: PropertyName("description_key")
        var description: String? = null,
)
