package com.neocaptainnemo.cv.ui.projects;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.databinding.FragmentProjectsBinding;
import com.neocaptainnemo.cv.model.Project;
import com.neocaptainnemo.cv.ui.IMainView;

import java.util.ArrayList;
import java.util.List;

public class ProjectsFragment extends Fragment implements ProjectsAdapter.OnProjectClicked, ValueEventListener {

    public static final String TAG = "ProjectsFragment";
    private static final String PROJECTS = "projects";
    private ProjectsAdapter adapter;
    private FragmentProjectsBinding binding;
    private boolean loaded;
    private Filter filter;

    private IMainView mainView;
    private List<Project> cachedData;

    public static ProjectsFragment instance() {
        return new ProjectsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IMainView) {
            mainView = (IMainView) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainView = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        adapter = new ProjectsAdapter(getContext());
        adapter.setOnProjectClicked(this);

        filter = Filter.ALL;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_projects, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.projects.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.projects.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_projects, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all:
                item.setChecked(true);

                filter = Filter.ALL;
                filterData();
                return true;

            case R.id.action_android:
                item.setChecked(true);

                filter = Filter.ANDROID;
                filterData();
                return true;
            case R.id.action_ios:
                item.setChecked(true);

                filter = Filter.IOS;
                filterData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference(PROJECTS)
                .addValueEventListener(this);
        if (mainView != null) {
            mainView.showProgress();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseDatabase.getInstance().getReference(PROJECTS)
                .removeEventListener(this);
        if (mainView != null) {
            mainView.hideProgress();
        }
    }

    @Override
    public void onProjectClicked(@NonNull Project project, View transitionView, View transitionView2) {
        Intent intent = new Intent(getContext(), ProjectDetailsActivity.class);
        intent.putExtra("project", project);

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(getContext());
        Bundle bundle = new Bundle();
        bundle.putString("project", project.name);

        analytics.logEvent("project_clicked", bundle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionView.setTransitionName(ProjectDetailsActivity.ICON_TRANSITION);
            transitionView2.setTransitionName(ProjectDetailsActivity.PLATFORM_TRANSITION);

            Pair<View, String> pair1 = Pair.create(transitionView, transitionView.getTransitionName());
            Pair<View, String> pair2 = Pair.create(transitionView2, transitionView2.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(getActivity(), pair1, pair2);
            getContext().startActivity(intent, options.toBundle());
        } else {
            getContext().startActivity(intent);
        }

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (mainView != null) {
            mainView.hideProgress();
        }

        if (!loaded) {
            GenericTypeIndicator<List<Project>> t = new GenericTypeIndicator<List<Project>>() {
            };

            cachedData = dataSnapshot.getValue(t);
            loaded = true;

            filterData();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        if (mainView != null) {
            mainView.hideProgress();
        }
        FirebaseCrash.report(databaseError.toException());
    }

    private void filterData() {

        List<Project> res = new ArrayList<>();

        for (Project project : cachedData) {
            if (filter.equals(Filter.ALL)) {
                res.add(project);
            }

            if (filter.equals(Filter.ANDROID) && project.platform.equals(Project.PLATFORM_ANDROID)) {
                res.add(project);
            }

            if (filter.equals(Filter.IOS) && project.platform.equals(Project.PLATFORM_IOS)) {
                res.add(project);
            }
        }

        adapter.clear();
        adapter.add(res);
        adapter.notifyDataSetChanged();

        if (res.isEmpty()) {
            binding.empty.setVisibility(View.VISIBLE);
        } else {
            binding.empty.setVisibility(View.GONE);
        }
    }

    enum Filter {ALL, ANDROID, IOS}
}
