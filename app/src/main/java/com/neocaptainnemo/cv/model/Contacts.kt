package com.neocaptainnemo.cv.model

import com.google.firebase.database.PropertyName

data class Contacts(

        @get: PropertyName("name")
        @set: PropertyName("name")
        var name: String? = null,

        @get: PropertyName("profession")
        @set: PropertyName("profession")
        var profession: String? = null,

        @get: PropertyName("phone")
        @set: PropertyName("phone")
        var phone: String? = null,

        @get: PropertyName("email")
        @set: PropertyName("email")
        var email: String? = null,

        @get: PropertyName("github")
        @set: PropertyName("github")
        var gitHub: String? = null,

        @get: PropertyName("cv_url")
        @set: PropertyName("cv_url")
        var cv: String? = null,

        @get: PropertyName("pic_url")
        @set: PropertyName("pic_url")
        var userPic: String? = null,

        @get: PropertyName("cv_url_key")
        @set: PropertyName("cv_url_key")
        var cvKey: String? = null,

        @get: PropertyName("name_key")
        @set: PropertyName("name_key")
        var nameKey: String? = null,

        @get: PropertyName("profession_key")
        @set: PropertyName("profession_key")
        var professionKey: String? = null
)
