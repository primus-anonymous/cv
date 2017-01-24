package com.neocaptainnemo.cv.ui.projects;

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
import android.view.View;
import android.view.ViewGroup;

import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.databinding.FragmentProjectsBinding;

public class ProjectsFragment extends Fragment implements ProjectsAdapter.OnProjectClicked {

    public static final String TAG = "ProjectsFragment";
    private ProjectsAdapter adapter;
    private FragmentProjectsBinding binding;

    public static ProjectsFragment instance() {
        return new ProjectsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ProjectsAdapter(getContext());
        adapter.setOnProjectClicked(this);
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

        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());
        adapter.add(new Object());

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onProjectClicked(@NonNull Object project, View transitionView, View transitionView2) {
        Intent intent = new Intent(getContext(), ProjectDetailsActivity.class);

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
}
