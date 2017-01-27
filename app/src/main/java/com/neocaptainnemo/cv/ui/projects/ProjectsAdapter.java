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
import com.neocaptainnemo.cv.model.Project;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    private Context context;

    private List<Project> data = new ArrayList<>();
    private OnProjectClicked onProjectClicked;

    ProjectsAdapter(Context context) {
        this.context = context;
    }

    void clear() {
        data.clear();
    }

    void add(Collection<Project> dataToAdd) {
        data.addAll(dataToAdd);
    }

    void add(Project item) {
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

        Project project = data.get(position);

        holder.binding.infoText.setText(project.name);
        Picasso.with(context)
                .load(project.webPic)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.binding.projImage);

        if (project.platform.equals(Project.PLATFORM_ANDROID)) {
            holder.binding.platform.setImageResource(R.drawable.ic_android);
        } else {
            holder.binding.platform.setImageResource(R.drawable.ic_apple);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    void setOnProjectClicked(OnProjectClicked onProjectClicked) {
        this.onProjectClicked = onProjectClicked;
    }

    interface OnProjectClicked {
        void onProjectClicked(@NonNull Project project, View transitionView, View transitionView2);
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
