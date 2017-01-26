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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.neocaptainnemo.cv.databinding.ActivityMainBinding;
import com.neocaptainnemo.cv.model.Contacts;
import com.neocaptainnemo.cv.ui.IMainView;
import com.neocaptainnemo.cv.ui.common.CommonFragment;
import com.neocaptainnemo.cv.ui.projects.ProjectsFragment;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ValueEventListener, IMainView {

    public static final String CONTACTS = "contacts";
    private ActivityMainBinding binding;
    private Contacts contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, ProjectsFragment.instance(), ProjectsFragment.TAG)
                    .commit();
            getSupportActionBar().setTitle(R.string.projects);
        }

        binding.download.setOnClickListener(view -> {
            if (contacts != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.cv));
                startActivity(intent);
            }

        });
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
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, ProjectsFragment.instance(), ProjectsFragment.TAG)
                        .commit();
                getSupportActionBar().setTitle(R.string.projects);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.action_common:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, CommonFragment.instance(), CommonFragment.TAG)
                        .commit();
                getSupportActionBar().setTitle(R.string.app_name);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.action_email:
                if (contacts != null) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", contacts.email, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Разработка мобильного приложения");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }

                break;

            case R.id.action_phone:
                if (contacts != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contacts.phone, null));
                    startActivity(intent);
                }
                break;

            case R.id.action_github:
                if (contacts != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contacts.gitHub));
                    startActivity(intent);
                }
                break;
        }


        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase.getInstance().getReference(CONTACTS)
                .addValueEventListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDatabase.getInstance().getReference(CONTACTS)
                .removeEventListener(this);

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        contacts = dataSnapshot.getValue(Contacts.class);

        TextView name = (TextView) binding.navView.findViewById(R.id.name);
        name.setText(contacts.name);

        TextView prof = (TextView) binding.navView.findViewById(R.id.profession);
        prof.setText(contacts.profession);

        ImageView userPic = (ImageView) binding.navView.findViewById(R.id.user_pic);
        Picasso.with(MainActivity.this)
                .load(contacts.userPic)
                .placeholder(R.drawable.placeholder)
                .into(userPic);

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

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
