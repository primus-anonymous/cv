package com.neocaptainnemo.cv.core.model

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
@Keep
class ContactsResponse {

    @PropertyName("contacts")
    var contacts: Contacts? = null

    @PropertyName("resources")
    var resources: HashMap<String, Map<String, String>> = hashMapOf()
}
