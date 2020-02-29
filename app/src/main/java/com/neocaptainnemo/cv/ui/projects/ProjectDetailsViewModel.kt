package com.neocaptainnemo.cv.ui.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.Project

class ProjectDetailsViewModel(
        private val app: Application,
        private val project: Project
) : AndroidViewModel(app) {

    private val projectLiveData = MutableLiveData(project)

    val shareUrl: String
        get() =
            StringBuilder().apply {
                append(app.getString(R.string.project))
                append(' ')
                append(project.name)

                if (project.storeUrl.isNullOrEmpty().not()) {
                    append(' ')
                    append(project.storeUrl)
                }

                if (project.gitHub.isNullOrEmpty().not()) {
                    append(' ')
                    append(app.getString(R.string.code))
                    append(' ')
                    append(project.gitHub)
                }

            }.toString()

    val gitHubUrl: String?
        get() = project.gitHub

    val storeUrl: String?
        get() = project.storeUrl

    val storeVisibility: LiveData<Boolean> = projectLiveData.map { it.storeUrl.isNullOrBlank().not() }

    val gitHubVisibility: LiveData<Boolean> = projectLiveData.map { it.gitHub.isNullOrBlank().not() }

    val platformImage: LiveData<Int> = projectLiveData.map {
        if (project.platform == Project.PLATFORM_ANDROID) {
            R.drawable.ic_android
        } else {
            R.drawable.ic_apple
        }
    }

    val webPic: LiveData<String> = projectLiveData.map { it.webPic.orEmpty() }

    val coverPic: LiveData<String> = projectLiveData.map { it.coverPic.orEmpty() }

    val projectName: LiveData<String> = projectLiveData.map { it.name.orEmpty() }

    val stack: LiveData<String> = projectLiveData.map { it.stack.orEmpty() }

    val company: LiveData<String> = projectLiveData.map { it.vendor.orEmpty() }

    val duties: LiveData<String> = projectLiveData.map { it.duties.orEmpty() }

    val detailsDescription: LiveData<String> = projectLiveData.map { it.description.orEmpty() }

    val sourceCode: LiveData<String> = projectLiveData.map { it.gitHub.orEmpty() }

}