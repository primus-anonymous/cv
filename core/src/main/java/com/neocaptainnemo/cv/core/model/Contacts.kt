package com.neocaptainnemo.cv.core.model

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
@Keep
data class Contacts(

        @get: PropertyName("name_key")
        @set: PropertyName("name_key")
        var name: String? = null,

        @get: PropertyName("profession_key")
        @set: PropertyName("profession_key")
        var profession: String? = null,

        @get: PropertyName("phone")
        @set: PropertyName("phone")
        var phone: String? = null,

        @get: PropertyName("email")
        @set: PropertyName("email")
        var email: String? = null,

        @get: PropertyName("github")
        @set: PropertyName("github")
        var github: String? = null,

        @get: PropertyName("pic_url")
        @set: PropertyName("pic_url")
        var userPic: String? = null,

        @get: PropertyName("cv_url_key")
        @set: PropertyName("cv_url_key")
        var cvUrl: String? = null,

        @get: PropertyName("telegram")
        @set: PropertyName("telegram")
        var telegram: String? = null,
)
