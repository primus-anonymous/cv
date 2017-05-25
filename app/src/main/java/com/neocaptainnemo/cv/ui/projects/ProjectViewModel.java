package com.neocaptainnemo.cv.ui.projects;

import android.support.annotation.DrawableRes;

import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.model.Project;

public class ProjectViewModel {

    private Project project;

    ProjectViewModel(Project project) {
        this.project = project;
    }

    public String getPic() {
        return this.project.webPic;
    }

    public String getName() {
        return this.project.name;
    }

    @DrawableRes
    public int getPlatform() {
        if (project.platform.equals(Project.PLATFORM_ANDROID)) {
            return R.drawable.ic_android;
        } else {
            return R.drawable.ic_apple;
        }
    }

}
