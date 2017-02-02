package com.neocaptainnemo.cv.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Map;

@IgnoreExtraProperties
public class ContactsResponse {

    @PropertyName("contacts")
    public Contacts contacts;

    @PropertyName("resources")
    public Map<String, Map<String, String>> resources;


}
