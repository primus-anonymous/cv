package com.neocaptainnemo.cv.ui.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ProjectDetailsViewModel(
        private val app: Application,
        private val project: Project,
) : AndroidViewModel(app) {

    private val projectFlow = MutableStateFlow(project)

    val shareUrl: String
        get() =
            StringBuilder().apply {
                append(app.getString(R.string.project))
                append(' ')
                append(project.name)

                if (project.storeUrl.isNullOrEmpty()
                                .not()) {
                    append(' ')
                    append(project.storeUrl)
                }

                if (project.gitHub.isNullOrEmpty()
                                .not()) {
                    append(' ')
                    append(app.getString(R.string.code))
                    append(' ')
                    append(project.gitHub)
                }

            }
                    .toString()

    val gitHubUrl: String?
        get() = project.gitHub

    val storeUrl: String?
        get() = project.storeUrl

    val storeVisibility: Flow<Boolean> = projectFlow
            .map {
                it.storeUrl.isNullOrBlank()
                        .not()
            }

    val gitHubVisibility: Flow<Boolean> = projectFlow
            .map {
                it.gitHub.isNullOrBlank()
                        .not()
            }

    val platformImage: Flow<Int> = projectFlow
            .map {
                if (project.platform == Project.PLATFORM_ANDROID) {
                    R.drawable.ic_android
                } else {
                    R.drawable.ic_apple
                }
            }

    val webPic: Flow<String> = projectFlow
            .map { it.webPic.orEmpty() }

    val coverPic: Flow<String> = projectFlow
            .map { it.coverPic.orEmpty() }

    val projectName: Flow<String> = projectFlow
            .map { it.name.orEmpty() }

    val stack: Flow<String> = projectFlow
            .map { it.stack.orEmpty() }

    val company: Flow<String> = projectFlow
            .map { it.vendor.orEmpty() }

    val duties: Flow<String> = projectFlow
            .map { it.duties.orEmpty() }

    val detailsDescription: Flow<String> = projectFlow
            .map { it.description.orEmpty() }

    val sourceCode: Flow<String> = projectFlow
            .map { it.gitHub.orEmpty() }
}