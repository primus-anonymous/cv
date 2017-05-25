package com.neocaptainnemo.cv.ui;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.neocaptainnemo.cv.R;

public final class Bindings {

    private Bindings() {

    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext().getApplicationContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageView);
    }

    @BindingAdapter("android:src")
    public static void setImageRes(ImageView imageView, @DrawableRes int drawable) {
        imageView.setImageResource(drawable);
    }

    @BindingAdapter("android:visibility")
    public static void setFabVisibility(FloatingActionButton fab, boolean visible) {
        if (visible) {
            fab.show();
        } else {
            fab.hide();
        }
    }
}
