package com.neocaptainnemo.cv.ui.projects;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.model.Project;

public class ProjectDetailsViewModel {

    public ObservableInt gitHubVisibility = new ObservableInt(View.GONE);
    public ObservableBoolean storeVisibility = new ObservableBoolean(false);
    private Context context;
    private Project project;
    private FirebaseAnalytics analytics;

    ProjectDetailsViewModel(Context context, Project project, FirebaseAnalytics analytics) {
        this.context = context;
        this.project = project;
        this.analytics = analytics;
        this.gitHubVisibility.set(TextUtils.isEmpty(project.gitHub) ? View.GONE : View.VISIBLE);
        this.storeVisibility.set(!TextUtils.isEmpty(project.storeUrl));
    }

    public String getTitle() {
        return project.name;
    }

    public String getCompany() {
        return project.vendor;
    }

    public String getCoverPic() {
        return project.coverPic;
    }

    public String getPic() {
        return this.project.webPic;
    }

    @DrawableRes
    public int getPlatform() {
        if (project.platform.equals(Project.PLATFORM_ANDROID)) {
            return R.drawable.ic_android;
        } else {
            return R.drawable.ic_apple;
        }
    }

    public Spanned getDescription() {
        return Html.fromHtml(project.description);
    }

    public Spanned getDuties() {
        return Html.fromHtml(project.duties);
    }

    public Spanned getStack() {
        return Html.fromHtml(project.stack);
    }

    public String getSourceCode() {
        return project.gitHub;
    }

    public void openGitHub() {
        Bundle bundle = new Bundle();
        bundle.putString("project", project.name);
        analytics.logEvent("project_source_code_clicked", bundle);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(project.gitHub));
        try {
            context.startActivity(intent);

        } catch (ActivityNotFoundException exception) {
            //do nothing
        }
    }
}
