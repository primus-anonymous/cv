package com.neocaptainnemo.cv.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
class
ProjectsResponse {

    @PropertyName("projects")
    var projects: ArrayList<Project> = arrayListOf()

    @PropertyName("resources")
    var resources: HashMap<String, Map<String, String>> = hashMapOf()

}
