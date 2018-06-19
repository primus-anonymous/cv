package com.neocaptainnemo.cv.model

import com.google.firebase.database.PropertyName


class CommonSection {

    @set: PropertyName("title")
    @get: PropertyName("title")
    var title: String? = null

    @set: PropertyName("description")
    @get: PropertyName("description")
    var description: String? = null

    @set: PropertyName("title_key")
    @get: PropertyName("title_key")
    var titleKey: String? = null

    @set: PropertyName("description_key")
    @get: PropertyName("description_key")
    var descriptionKey: String? = null
}
