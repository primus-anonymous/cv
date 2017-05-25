package com.neocaptainnemo.cv.ui.common;

import android.text.Html;
import android.text.Spanned;

import com.neocaptainnemo.cv.model.CommonSection;

public class CommonViewModel {

    private CommonSection commonSection;

    public CommonViewModel(CommonSection commonSection) {
        this.commonSection = commonSection;
    }

    public String getTitle() {
        return commonSection.title;
    }

    public Spanned getDescription() {
        return Html.fromHtml(commonSection.description);
    }
}
