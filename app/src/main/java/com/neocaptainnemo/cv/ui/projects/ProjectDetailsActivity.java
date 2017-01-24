package com.neocaptainnemo.cv.ui.projects;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.databinding.ActivityProjectDetailsBinding;
import com.squareup.picasso.Picasso;

public class ProjectDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ICON_TRANSITION = "ICON_TRANSITION";
    public static final String PLATFORM_TRANSITION = "PLATFORM_TRANSITION";
    private ActivityProjectDetailsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_details);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        Picasso.with(this).load("http://risovach.ru/upload/2013/04/mem/golub_15869510_orig_.jpg")
                .into(binding.projImage);

        Picasso.with(this)
                .load("https://lh3.googleusercontent.com/9UrH-sZg4ELIA9VJvFBiEpyKzSCtL2wIGchlGQ9F9UCjxiYjtzVysAIMoVcT41fre5RpChaE2QLNvdojHKFvAnFLP0omdKI=s688")
                .into(binding.logo);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "extra_image");


        //binding.collapsingToolbar.setTitle("Very Good Transfer");
        binding.collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        binding.title.setText("Very Good Transfer");
        binding.company.setText("Enterra Inc");

        binding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                Rect scrollBounds = new Rect();
                binding.nestedScroll.getHitRect(scrollBounds);
                if (binding.title.getLocalVisibleRect(scrollBounds)) {

                    binding.collapsingToolbar.setTitle("");
                } else {
                    // NONE of the imageView is within the visible window
                    binding.collapsingToolbar.setTitle("Very Good Transfer");

                }

            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.projImage.setTransitionName(ICON_TRANSITION);
            binding.platform.setTransitionName(PLATFORM_TRANSITION);
        }

        binding.store.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_project_details, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);

        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This is the text that will be shared.");

        shareActionProvider.setShareIntent(shareIntent);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.store:
                Toast.makeText(this, "Store", Toast.LENGTH_LONG).show();
                break;

            default:
                //do nothing
                break;
        }
    }
}
