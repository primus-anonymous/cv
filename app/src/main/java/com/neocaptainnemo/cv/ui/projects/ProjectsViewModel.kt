package com.neocaptainnemo.cv.ui.projects

import androidx.lifecycle.ViewModel
import com.neocaptainnemo.cv.core.data.DataService
import com.neocaptainnemo.cv.core.model.Filter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class ProjectsViewModel(private val dataService: DataService) : ViewModel() {

    private val _empty = ConflatedBroadcastChannel(false)

    private val _progress = ConflatedBroadcastChannel(false)

    private val _filter = ConflatedBroadcastChannel(Filter.ALL)

    val empty: Flow<Boolean> = _empty.asFlow()

    val progress: Flow<Boolean> = _progress.asFlow()

    var filter: Filter
        get() = _filter.value
        set(value) {
            _filter.offer(value)
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
            .combine(_filter.asFlow(),
                     this::transform)
            .onStart {
                _progress.offer(true)
            }
            .onEach {
                _progress.offer(false)
                _empty.offer(it.isEmpty())
            }

}