package com.neocaptainnemo.cv.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
class ContactsResponse {

    @PropertyName("projects")
    var contacts: Contacts? = null

    @PropertyName("resources")
    var resources: HashMap<String, Map<String, String>> = hashMapOf()


}
