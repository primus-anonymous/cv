package com.neocaptainnemo.cv.ui.common

import androidx.lifecycle.ViewModel
import com.neocaptainnemo.cv.core.data.DataService
import com.neocaptainnemo.cv.core.model.CommonSection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class CommonViewModel(private val dataService: DataService) : ViewModel() {

    private val _empty = ConflatedBroadcastChannel(false)

    private val _progress = ConflatedBroadcastChannel(false)

    private val _items = ConflatedBroadcastChannel(listOf<CommonSection>())

    val empty: Flow<Boolean> = _empty.asFlow()

    val progress: Flow<Boolean> = _progress.asFlow()

    fun commons(): Flow<List<CommonSection>> = dataService.commons()
            .catch {
                emit(listOf())
            }
            .onStart {
                _progress.offer(true)
            }
            .onEach {
                _progress.offer(false)
                _empty.offer(it.isEmpty())
            }

}