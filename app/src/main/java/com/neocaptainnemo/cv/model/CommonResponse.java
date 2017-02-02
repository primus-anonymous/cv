package com.neocaptainnemo.cv.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class CommonResponse {

    @PropertyName("common")
    public Common common;

    @PropertyName("resources")
    public Map<String, Map<String, String>> resources;

    @IgnoreExtraProperties
    public static class Common {
        @PropertyName("sections")
        public List<CommonSection> sections;
    }
}
