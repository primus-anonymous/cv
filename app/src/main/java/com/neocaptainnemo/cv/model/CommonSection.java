package com.neocaptainnemo.cv.model;

import com.google.firebase.database.PropertyName;

public class CommonSection {

    @PropertyName("title")
    public String title;

    @PropertyName("description")
    public String description;

    public CommonSection() {
        //do nothing
    }
}
