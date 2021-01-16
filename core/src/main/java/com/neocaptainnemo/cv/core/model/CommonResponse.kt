package com.neocaptainnemo.cv.core.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
class CommonResponse {

    @PropertyName("common")
    var common: Common? = null

    @PropertyName("resources")
    var resources: HashMap<String, Map<String, String>> = hashMapOf()

    @IgnoreExtraProperties
    class Common {
        @PropertyName("sections")
        var sections: ArrayList<CommonSection> = arrayListOf()
    }
}
