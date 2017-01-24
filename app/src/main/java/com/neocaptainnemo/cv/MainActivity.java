package com.neocaptainnemo.cv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.neocaptainnemo.cv.ui.common.CommonFragment;
import com.neocaptainnemo.cv.ui.contacts.ContactsFragment;
import com.neocaptainnemo.cv.ui.projects.ProjectsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.action_common);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_projects:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, ProjectsFragment.instance(), ProjectsFragment.TAG)
                        .commit();
                getSupportActionBar().setTitle(R.string.projects);
                break;
            case R.id.action_contacts:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, ContactsFragment.instance(), ContactsFragment.TAG)
                        .commit();
                getSupportActionBar().setTitle(R.string.action_contacts);
                break;

            case R.id.action_common:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, CommonFragment.instance(), CommonFragment.TAG)
                        .commit();
                getSupportActionBar().setTitle(R.string.app_name);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
