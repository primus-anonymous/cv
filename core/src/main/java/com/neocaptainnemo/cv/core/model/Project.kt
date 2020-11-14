package com.neocaptainnemo.cv.core.model

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Project(
        @get: PropertyName("web_pic")
        @set: PropertyName("web_pic")
        var webPic: String? = null,
        @get: PropertyName("platform")
        @set: PropertyName("platform")
        var platform: String? = null,
        @get: PropertyName("vendor")
        @set: PropertyName("vendor")
        var vendor: String? = null,
        @get: PropertyName("cover_pic")
        @set: PropertyName("cover_pic")
        var coverPic: String? = null,
        @get: PropertyName("stack")
        @set: PropertyName("stack")
        var stack: String? = null,
        @get: PropertyName("store_url")
        @set: PropertyName("store_url")
        var storeUrl: String? = null,
        @get: PropertyName("git_hub")
        @set: PropertyName("git_hub")
        var gitHub: String? = null,
        @get: PropertyName("description_key")
        @set: PropertyName("description_key")
        var description: String? = null,
        @get: PropertyName("duties_key")
        @set: PropertyName("duties_key")
        var duties: String? = null,
        @get: PropertyName("name_key")
        @set: PropertyName("name_key")
        var name: String? = null,
) : Parcelable {

    companion object {

        const val PLATFORM_ANDROID = "android"

        const val PLATFORM_IOS = "ios"
    }
}
