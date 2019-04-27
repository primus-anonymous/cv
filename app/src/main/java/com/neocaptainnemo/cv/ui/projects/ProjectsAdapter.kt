package com.neocaptainnemo.cv.ui.projects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.ui.ArrayAdapter
import kotlinx.android.synthetic.main.item_project.view.*

class ProjectsAdapter : ArrayAdapter<Project, ProjectsAdapter.ProjectViewHolder>() {

    var onProjectClicked: ((project: Project, transitionView: View, transitionView2: View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder =
            ProjectViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false), this)

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {

        val project = data[position]

        holder.root.infoText.text = project.name

        val requestOption = RequestOptions().error(R.drawable.placeholder).placeholder(R.drawable.placeholder)

        Glide.with(holder.root)
                .applyDefaultRequestOptions(requestOption)
                .load(project.webPic)
                .into(holder.root.image)

        if (project.platform == Project.PLATFORM_ANDROID) {
            holder.root.itemPlatform.setImageResource(R.drawable.ic_android)
        }

        if (project.platform == Project.PLATFORM_IOS) {
            holder.root.itemPlatform.setImageResource(R.drawable.ic_apple)
        }
    }


    class ProjectViewHolder(var root: View, adapter: ProjectsAdapter) : RecyclerView.ViewHolder(root) {

        init {
            root
                    .setOnClickListener {
                        adapter.onProjectClicked?.invoke(adapter.data[adapterPosition],
                                root.image, root.itemPlatform)
                    }
        }
    }

}
