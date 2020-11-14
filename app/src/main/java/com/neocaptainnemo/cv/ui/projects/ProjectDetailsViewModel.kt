package com.neocaptainnemo.cv.ui.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.Project
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

@FlowPreview
@ExperimentalCoroutinesApi
class ProjectDetailsViewModel(
        private val app: Application,
        private val project: Project,
) : AndroidViewModel(app) {

    private val projectChannel = ConflatedBroadcastChannel(project)

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

    val storeVisibility: Flow<Boolean> = projectChannel
            .asFlow()
            .map {
                it.storeUrl.isNullOrBlank()
                        .not()
            }

    val gitHubVisibility: Flow<Boolean> = projectChannel
            .asFlow()
            .map {
                it.gitHub.isNullOrBlank()
                        .not()
            }

    val platformImage: Flow<Int> = projectChannel
            .asFlow()
            .map {
                if (project.platform == Project.PLATFORM_ANDROID) {
                    R.drawable.ic_android
                } else {
                    R.drawable.ic_apple
                }
            }

    val webPic: Flow<String> = projectChannel
            .asFlow()
            .map { it.webPic.orEmpty() }

    val coverPic: Flow<String> = projectChannel
            .asFlow()
            .map { it.coverPic.orEmpty() }

    val projectName: Flow<String> = projectChannel
            .asFlow()
            .map { it.name.orEmpty() }

    val stack: Flow<String> = projectChannel
            .asFlow()
            .map { it.stack.orEmpty() }

    val company: Flow<String> = projectChannel
            .asFlow()
            .map { it.vendor.orEmpty() }

    val duties: Flow<String> = projectChannel
            .asFlow()
            .map { it.duties.orEmpty() }

    val detailsDescription: Flow<String> = projectChannel
            .asFlow()
            .map { it.description.orEmpty() }

    val sourceCode: Flow<String> = projectChannel
            .asFlow()
            .map { it.gitHub.orEmpty() }

}