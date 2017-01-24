package com.neocaptainnemo.cv.ui.projects;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neocaptainnemo.cv.R;
import com.neocaptainnemo.cv.databinding.ItemProjectBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    private Context context;

    private List<Object> data = new ArrayList<>();
    private OnProjectClicked onProjectClicked;

    ProjectsAdapter(Context context) {
        this.context = context;
    }

    void clear() {
        data.clear();
    }

    void add(Collection dataToAdd) {
        data.addAll(dataToAdd);
    }

    void add(Object item) {
        data.add(item);
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemProjectBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_project, parent, false);

        return new ProjectViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {

        holder.binding.infoText.setText("Project");
        Picasso.with(context)
                .load("http://risovach.ru/upload/2013/04/mem/golub_15869510_orig_.jpg")
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.projImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    void setOnProjectClicked(OnProjectClicked onProjectClicked) {
        this.onProjectClicked = onProjectClicked;
    }

    interface OnProjectClicked {
        void onProjectClicked(@NonNull Object project, View transitionView, View transitionView2);
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {

        ItemProjectBinding binding;

        ProjectViewHolder(ItemProjectBinding binding, ProjectsAdapter adapter) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot()
                    .setOnClickListener(view -> {
                        if (adapter.onProjectClicked != null) {
                            adapter.onProjectClicked.onProjectClicked(adapter.data.get(getAdapterPosition()),
                                    this.binding.projImage, this.binding.platform);
                        }
                    });
        }
    }
}
