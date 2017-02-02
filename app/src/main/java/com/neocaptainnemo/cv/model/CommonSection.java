package com.neocaptainnemo.cv.model;

import com.google.firebase.database.PropertyName;

public class CommonSection {

    @PropertyName("title")
    public String title;

    @PropertyName("description")
    public String description;

    @PropertyName("title_key")
    public String titleKey;

    @PropertyName("description_key")
    public String descriptionKey;


    public CommonSection() {
        //do nothing
    }
}
