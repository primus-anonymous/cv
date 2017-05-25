package com.neocaptainnemo.cv;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.neocaptainnemo.cv.databinding.ActivityMainBinding;
import com.neocaptainnemo.cv.model.Contacts;
import com.neocaptainnemo.cv.model.ContactsResponse;
import com.neocaptainnemo.cv.ui.IMainView;
import com.neocaptainnemo.cv.ui.common.CommonFragment;
import com.neocaptainnemo.cv.ui.projects.ProjectsFragment;

import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainView {

    private ActivityMainBinding binding;
    private Contacts contacts;
    private FirebaseAnalytics analytics;
    private int selectedSection;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        analytics = FirebaseAnalytics.getInstance(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            binding.navView.setCheckedItem(R.id.action_projects);
            showProjects();
        } else {
            int section = savedInstanceState.getInt("section", R.id.action_projects);
            if (section == R.id.action_projects) {
                getSupportActionBar().setTitle(R.string.projects);
            } else {
                getSupportActionBar().setTitle(R.string.action_common);
            }
        }

        binding.download.setOnClickListener(view -> {
            if (contacts != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.cv));
                startActivity(intent);
                analytics.logEvent("download_cv_pressed", null);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("section", selectedSection);
    }

    private void showProjects() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, ProjectsFragment.instance(), ProjectsFragment.TAG)
                .commit();
        getSupportActionBar().setTitle(R.string.projects);
        selectedSection = R.id.action_projects;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_projects:
                if (selectedSection != R.id.action_projects) {
                    showProjects();
                    analytics.logEvent("drawer_projects_selected", null);
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.action_common:
                if (selectedSection != R.id.action_common) {
                    showCommon();
                    analytics.logEvent("drawer_common_selected", null);
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.action_email:
                if (contacts != null) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", contacts.email, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject));
                    startActivity(Intent.createChooser(emailIntent, getString(R.string.mail_chooser)));
                    analytics.logEvent("contact_email", null);
                }

                break;

            case R.id.action_phone:
                if (contacts != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contacts.phone, null));
                    startActivity(intent);
                    analytics.logEvent("contact_phone", null);
                }
                break;

            case R.id.action_github:
                if (contacts != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.gitHub));
                    startActivity(intent);
                    analytics.logEvent("contact_github", null);
                }
                break;
        }


        return true;
    }

    private void showCommon() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, CommonFragment.instance(), CommonFragment.TAG)
                .commit();
        getSupportActionBar().setTitle(R.string.action_common);
        selectedSection = R.id.action_common;
    }

    @Override
    protected void onStart() {
        super.onStart();

        subscription = RxFirebaseDatabase
                .observeValueEvent(FirebaseDatabase.getInstance().getReference(), ContactsResponse.class)
                .map(contactsResponse -> {

                    Map<String, String> strings = contactsResponse.resources.get(
                            LocaleService.getInstance().getLocale(MainActivity.this));

                    Contacts contacts = contactsResponse.contacts;
                    contacts.cv = strings.get(contacts.cvKey);
                    contacts.name = strings.get(contacts.nameKey);
                    contacts.profession = strings.get(contacts.professionKey);

                    return contacts;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cont -> {
                    contacts = cont;

                    TextView name = (TextView) binding.navView.findViewById(R.id.name);
                    name.setText(contacts.name);

                    TextView prof = (TextView) binding.navView.findViewById(R.id.profession);
                    prof.setText(contacts.profession);

                    ImageView userPic = (ImageView) binding.navView.findViewById(R.id.user_pic);
                    Glide.with(MainActivity.this)
                            .load(contacts.userPic)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(userPic);

                }, FirebaseCrash::report);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


    @Override
    public void showProgress() {
        binding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progress.setVisibility(View.GONE);
    }
}
