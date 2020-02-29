package com.neocaptainnemo.cv.ui.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neocaptainnemo.cv.model.Filter
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.IDataService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class ProjectsViewModel(private val dataService: IDataService) : ViewModel() {

    private val _empty = MutableLiveData<Boolean>(false)

    private val _progress = MutableLiveData<Boolean>(false)

    private val _filter = ConflatedBroadcastChannel(Filter.ALL)

    val empty: LiveData<Boolean> = _empty

    val progress: LiveData<Boolean> = _progress

    var filter: Filter
        get() = _filter.value
        set(value) {
            _filter.offer(value)
        }

    private suspend fun transform(projects: List<Project>, filter: Filter): List<Project> =
            projects.filter {
                filter == Filter.ALL ||
                        it.platform == Project.PLATFORM_ANDROID && filter == Filter.ANDROID ||
                        it.platform == Project.PLATFORM_IOS && filter == Filter.IOS
            }


    fun projects(): LiveData<List<Project>> = dataService.projects()
            .catch {
                emit(listOf())
            }
            .combine(_filter.asFlow(), this::transform)
            .onStart {
                _progress.value = true
            }
            .onEach {
                _progress.value = false
                _empty.value = it.isEmpty()
            }
            .asLiveData()

}