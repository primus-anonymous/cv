package com.neocaptainnemo.cv.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neocaptainnemo.cv.model.CommonSection
import com.neocaptainnemo.cv.services.IDataService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class CommonViewModel(private val dataService: IDataService) : ViewModel() {

    private val _empty = MutableLiveData<Boolean>(false)

    private val _progress = MutableLiveData<Boolean>(false)

    val empty: LiveData<Boolean> = _empty

    val progress: LiveData<Boolean> = _progress

    fun commons(): LiveData<List<CommonSection>> = dataService.commons()
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
            .asLiveData()

}