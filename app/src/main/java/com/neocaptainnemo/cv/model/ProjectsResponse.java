package com.neocaptainnemo.cv.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class ProjectsResponse {

    @PropertyName("projects")
    public List<Project> projects;

    @PropertyName("resources")
    public Map<String, Map<String, String>> resources;

}
