package com.neocaptainnemo.cv.core.model

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
@Keep
class CommonResponse {

    @PropertyName("common")
    var common: Common? = null

    @PropertyName("resources")
    var resources: HashMap<String, Map<String, String>> = hashMapOf()

    @IgnoreExtraProperties
    @Keep
    class Common {
        @PropertyName("sections")
        var sections: ArrayList<CommonSection> = arrayListOf()
    }
}
