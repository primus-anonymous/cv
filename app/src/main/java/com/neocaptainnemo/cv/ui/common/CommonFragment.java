package com.neocaptainnemo.cv.ui.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.neocaptainnemo.cv.LocaleService;
import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.databinding.FragmentCommonBinding;
import com.neocaptainnemo.cv.model.CommonResponse;
import com.neocaptainnemo.cv.model.CommonSection;
import com.neocaptainnemo.cv.ui.IMainView;

import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CommonFragment extends Fragment {

    public static final String TAG = "CommonFragment";
    private FragmentCommonBinding binding;
    private CommonAdapter adapter;
    private IMainView mainView;
    private Subscription subscription;

    public static CommonFragment instance() {
        return new CommonFragment();
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

        setRetainInstance(true);
        adapter = new CommonAdapter(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.commonList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.commonList.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_common, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        subscription = RxFirebaseDatabase
                .observeValueEvent(FirebaseDatabase.getInstance().getReference(), CommonResponse.class)
                .map(commonResponse -> {

                    Map<String, String> strings = commonResponse.resources.get(
                            LocaleService.getInstance().getLocale(getContext()));

                    for (CommonSection section : commonResponse.common.sections) {
                        section.description = strings.get(section.descriptionKey);
                        section.title = strings.get(section.titleKey);
                    }

                    return commonResponse.common.sections;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(projects -> {
                    if (mainView != null) {
                        mainView.hideProgress();
                    }
                    adapter.clear();
                    adapter.add(projects);
                    adapter.notifyDataSetChanged();

                    if (projects.isEmpty()) {
                        binding.empty.setVisibility(View.VISIBLE);
                    } else {
                        binding.empty.setVisibility(View.GONE);
                    }


                }, throwable -> {
                    if (mainView != null) {
                        mainView.hideProgress();
                    }
                    FirebaseCrash.report(throwable);
                });


        if (mainView != null) {
            mainView.showProgress();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        if (mainView != null) {
            mainView.hideProgress();
        }

    }
}
