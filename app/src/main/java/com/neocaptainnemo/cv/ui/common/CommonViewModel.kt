package com.neocaptainnemo.cv.ui.common

import androidx.lifecycle.ViewModel
import com.neocaptainnemo.cv.core.data.DataService
import com.neocaptainnemo.cv.core.model.CommonSection
import kotlinx.coroutines.flow.*

class CommonViewModel(private val dataService: DataService) : ViewModel() {

    private val _empty = MutableStateFlow(false)

    private val _progress = MutableStateFlow(false)

    val empty: Flow<Boolean> = _empty

    val progress: Flow<Boolean> = _progress

    fun commons(): Flow<List<CommonSection>> = dataService.commons()
            .catch {
                emit(listOf())
            }
            .onStart {
                _progress.value = true
            }
            .onEach {
                _progress.value = false
                _empty.value = it.isEmpty()
            }
}