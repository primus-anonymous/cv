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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.databinding.FragmentCommonBinding;
import com.neocaptainnemo.cv.model.CommonSection;
import com.neocaptainnemo.cv.ui.IMainView;

import java.util.List;

public class CommonFragment extends Fragment implements ValueEventListener {

    public static final String TAG = "CommonFragment";
    public static final String COMMON = "common";
    public static final String SECTIONS = "sections";
    private FragmentCommonBinding binding;
    private CommonAdapter adapter;
    private IMainView mainView;

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
        FirebaseDatabase.getInstance().getReference(COMMON)
                .child(SECTIONS)
                .addValueEventListener(this);
        if (mainView != null) {
            mainView.showProgress();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseDatabase.getInstance().getReference(COMMON)
                .child(SECTIONS)
                .removeEventListener(this);
        if (mainView != null) {
            mainView.hideProgress();
        }

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (mainView != null) {
            mainView.hideProgress();
        }

        GenericTypeIndicator<List<CommonSection>> t = new GenericTypeIndicator<List<CommonSection>>() {
        };

        List<CommonSection> data = dataSnapshot.getValue(t);
        adapter.clear();
        adapter.add(data);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        if (mainView != null) {
            mainView.hideProgress();
        }

        FirebaseCrash.report(databaseError.toException());

    }
}
