package com.neocaptainnemo.cv.ui.common

import androidx.lifecycle.ViewModel
import com.neocaptainnemo.cv.model.CommonSection
import com.neocaptainnemo.cv.services.IDataService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class CommonViewModel(private val dataService: IDataService) : ViewModel() {

    private val progressSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    private val emptySubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    fun commons(): Observable<List<CommonSection>> =
            dataService
                    .commons()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturnItem(listOf())
                    .doOnSubscribe { progressSubject.onNext(true) }
                    .doOnNext {
                        progressSubject.onNext(false)
                        emptySubject.onNext(it.isEmpty())
                    }


    val progress: Observable<Boolean> = progressSubject

    val empty: Observable<Boolean> = emptySubject

}