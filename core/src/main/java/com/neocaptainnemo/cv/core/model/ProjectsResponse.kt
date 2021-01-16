package com.neocaptainnemo.cv.core.model

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
@Keep
class
ProjectsResponse {

    @PropertyName("projects")
    var projects: ArrayList<Project> = arrayListOf()

    @PropertyName("resources")
    var resources: HashMap<String, Map<String, String>> = hashMapOf()

}
