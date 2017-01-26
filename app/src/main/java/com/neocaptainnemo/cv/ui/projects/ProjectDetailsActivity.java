package com.neocaptainnemo.cv.ui.projects;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.databinding.ActivityProjectDetailsBinding;
import com.neocaptainnemo.cv.model.Project;
import com.squareup.picasso.Picasso;

public class ProjectDetailsActivity extends AppCompatActivity {

    public static final String ICON_TRANSITION = "ICON_TRANSITION";
    public static final String PLATFORM_TRANSITION = "PLATFORM_TRANSITION";
    private ActivityProjectDetailsBinding binding;

    private Project project;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_details);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        project = getIntent().getParcelableExtra("project");

        Picasso.with(this)
                .load(project.webPic)
                .placeholder(R.drawable.placeholder)
                .into(binding.projImage);

        Picasso.with(this)
                .load(project.coverPic)
                .placeholder(R.drawable.placeholder)
                .into(binding.logo);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "extra_image");


        if (project.platform.equals(Project.PLATFORM_ANDROID)) {
            binding.platform.setImageResource(R.drawable.ic_android);
        } else {
            binding.platform.setImageResource(R.drawable.ic_apple);
        }
        binding.collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        binding.title.setText(project.name);
        binding.company.setText(project.vendor);

        binding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                Rect scrollBounds = new Rect();
                binding.nestedScroll.getHitRect(scrollBounds);
                if (binding.title.getLocalVisibleRect(scrollBounds)) {

                    binding.collapsingToolbar.setTitle("");
                } else {
                    binding.collapsingToolbar.setTitle(project.name);

                }

            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.projImage.setTransitionName(ICON_TRANSITION);
            binding.platform.setTransitionName(PLATFORM_TRANSITION);
        }

        binding.description.setText(project.description);
        binding.duties.setText(project.duties);
        binding.stack.setText(project.stack);

        if (!TextUtils.isEmpty(project.storeUrl)) {
            binding.store.show();
        } else {
            binding.store.hide();
        }

        if (TextUtils.isEmpty(project.gitHub)) {
            binding.sourceCodeTitle.setVisibility(View.GONE);
            binding.sourceCode.setVisibility(View.GONE);
        } else {
            binding.sourceCodeTitle.setVisibility(View.VISIBLE);
            binding.sourceCode.setVisibility(View.VISIBLE);

            binding.sourceCode.setText(project.gitHub);

            binding.sourceCode.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(project.gitHub));
                try {
                    startActivity(intent);

                } catch (ActivityNotFoundException exception) {

                    Snackbar snackbar = Snackbar
                            .make(binding.coordinatorLayout, R.string.cant_help_it, Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            });
        }

        binding.store.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(project.storeUrl));
            try {
                startActivity(intent);

            } catch (ActivityNotFoundException exception) {

                Snackbar snackbar = Snackbar
                        .make(binding.coordinatorLayout, R.string.cant_help_it, Snackbar.LENGTH_LONG);

                snackbar.show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_project_details, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);

        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.project));
        stringBuilder.append(' ');
        stringBuilder.append(project.name);

        if (!TextUtils.isEmpty(project.storeUrl)) {
            stringBuilder.append(' ');
            stringBuilder.append(project.storeUrl);
        }

        if (!TextUtils.isEmpty(project.gitHub)) {
            stringBuilder.append(' ');
            stringBuilder.append(getString(R.string.code));
            stringBuilder.append(' ');
            stringBuilder.append(project.gitHub);
        }


        shareIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());

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

}
