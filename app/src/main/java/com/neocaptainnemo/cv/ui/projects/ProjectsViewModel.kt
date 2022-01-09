package com.neocaptainnemo.cv.ui.projects

import androidx.lifecycle.ViewModel
import com.neocaptainnemo.cv.core.data.DataService
import com.neocaptainnemo.cv.core.model.Filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(private val dataService: DataService) : ViewModel() {

    private val _empty = MutableStateFlow(false)

    private val _progress = MutableStateFlow(false)

    private val _filter = MutableStateFlow(Filter.ALL)

    val empty: Flow<Boolean> = _empty

    val progress: Flow<Boolean> = _progress

    var filter: Filter
        get() = _filter.value
        set(value) {
            _filter.value = value
        }

    private suspend fun transform(
        projects: List<com.neocaptainnemo.cv.core.model.Project>,
        filter: Filter,
    ): List<com.neocaptainnemo.cv.core.model.Project> =
        projects.filter {
            filter == Filter.ALL ||
                it.platform == com.neocaptainnemo.cv.core.model.Project.PLATFORM_ANDROID && filter == Filter.ANDROID ||
                it.platform == com.neocaptainnemo.cv.core.model.Project.PLATFORM_IOS && filter == Filter.IOS
        }

    fun projects(): Flow<List<com.neocaptainnemo.cv.core.model.Project>> = dataService.projects()
        .catch {
            emit(listOf())
        }
        .combine(
            _filter,
            this::transform
        )
        .onStart {
            _progress.value = true
        }
        .onEach {
            _progress.value = false
            _empty.value = it.isEmpty()
        }
}
