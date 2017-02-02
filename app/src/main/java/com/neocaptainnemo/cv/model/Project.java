package com.neocaptainnemo.cv.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
public class Project implements Parcelable {

    public static final String PLATFORM_ANDROID = "android";

    public static final String PLATFORM_IOS = "ios";
    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
    @PropertyName("name")
    public String name;
    @PropertyName("description")
    public String description;
    @PropertyName("web_pic")
    public String webPic;
    @PropertyName("platform")
    public String platform;
    @PropertyName("vendor")
    public String vendor;
    @PropertyName("cover_pic")
    public String coverPic;
    @PropertyName("stack")
    public String stack;
    @PropertyName("duties")
    public String duties;
    @PropertyName("store_url")
    public String storeUrl;
    @PropertyName("git_hub")
    public String gitHub;
    @PropertyName("description_key")
    public String descriptionKey;
    @PropertyName("duties_key")
    public String dutiesKey;
    @PropertyName("name_key")
    public String nameKey;


    public Project() {
        //do nothing
    }

    protected Project(Parcel in) {
        name = in.readString();
        description = in.readString();
        webPic = in.readString();
        platform = in.readString();
        vendor = in.readString();
        coverPic = in.readString();
        stack = in.readString();
        duties = in.readString();
        storeUrl = in.readString();
        gitHub = in.readString();
        descriptionKey = in.readString();
        dutiesKey = in.readString();
        nameKey = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(webPic);
        parcel.writeString(platform);
        parcel.writeString(vendor);
        parcel.writeString(coverPic);
        parcel.writeString(stack);
        parcel.writeString(duties);
        parcel.writeString(storeUrl);
        parcel.writeString(gitHub);
        parcel.writeString(descriptionKey);
        parcel.writeString(dutiesKey);
        parcel.writeString(nameKey);
    }
}
