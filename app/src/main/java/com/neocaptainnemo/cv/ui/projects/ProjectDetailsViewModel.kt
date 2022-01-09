package com.neocaptainnemo.cv.ui.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.data.DataService
import com.neocaptainnemo.cv.core.model.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(
    private val dataService: DataService,
    private val app: Application,
) : AndroidViewModel(app) {

    private val _progress = MutableStateFlow(false)

    val progress: Flow<Boolean> = _progress

    private val _project: MutableStateFlow<Project?> = MutableStateFlow(null)

    fun projectDetails(projectId: String) {
        viewModelScope.launch {
            dataService
                .project(projectId)
                .onStart {
                    _progress.value = true
                }
                .onEach {
                    _progress.value = false
                }
                .collect {
                    _project.emit(it)
                }
        }
    }

    val shareUrl: String
        get() {
            val project = _project.value ?: return ""

            return StringBuilder().apply {
                append(app.getString(R.string.project))
                append(' ')
                append(project.name)

                if (project.storeUrl.isNullOrEmpty()
                    .not()
                ) {
                    append(' ')
                    append(project.storeUrl)
                }

                if (project.gitHub.isNullOrEmpty()
                    .not()
                ) {
                    append(' ')
                    append(app.getString(R.string.code))
                    append(' ')
                    append(project.gitHub)
                }
            }
                .toString()
        }

    val gitHubUrl: String?
        get() = _project.value?.gitHub

    val storeUrl: String?
        get() = _project.value?.storeUrl

    val storeVisibility: Flow<Boolean> = _project.filterNotNull()
        .map {
            it.storeUrl.isNullOrBlank()
                .not()
        }

    val gitHubVisibility: Flow<Boolean> = _project.filterNotNull()
        .map {
            it.gitHub.isNullOrBlank()
                .not()
        }

    val platformImage: Flow<Int> = _project.filterNotNull()
        .map { project ->
            if (project.platform == Project.PLATFORM_ANDROID) {
                R.drawable.ic_android
            } else {
                R.drawable.ic_apple
            }
        }

    val webPic: Flow<String> = _project.filterNotNull()
        .map { it.webPic.orEmpty() }

    val coverPic: Flow<String> = _project.filterNotNull()
        .map { it.coverPic.orEmpty() }

    val projectName: Flow<String> = _project.filterNotNull()
        .map { it.name.orEmpty() }

    val stack: Flow<String> = _project.filterNotNull()
        .map { it.stack.orEmpty() }

    val company: Flow<String> = _project.filterNotNull()
        .map { it.vendor.orEmpty() }

    val duties: Flow<String> = _project.filterNotNull()
        .map { it.duties.orEmpty() }

    val detailsDescription: Flow<String> = _project.filterNotNull()
        .map { it.description.orEmpty() }

    val sourceCode: Flow<String> = _project.filterNotNull()
        .map { it.gitHub.orEmpty() }
}
