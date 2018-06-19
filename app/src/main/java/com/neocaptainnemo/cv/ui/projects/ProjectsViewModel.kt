package com.neocaptainnemo.cv.ui.projects

import android.arch.lifecycle.ViewModel
import com.neocaptainnemo.cv.model.Filter
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.IDataService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ProjectsViewModel @Inject constructor(private val dataService: IDataService) : ViewModel() {

    private val progressSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    private val emptySubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    private val filterSubject: BehaviorSubject<Filter> = BehaviorSubject.createDefault(Filter.ALL)

    var filter: Filter
        get() = filterSubject.value
        set(value) = filterSubject.onNext(value)

    fun progress(): Observable<Boolean> = progressSubject

    fun empty(): Observable<Boolean> = emptySubject

    fun projects(): Observable<List<Project>> = Observable.combineLatest(dataService.projects(), filterSubject,
            BiFunction<List<Project>, Filter, List<Project>> { projects, filter ->

                projects.filter {
                    filter == Filter.ALL ||
                            it.platform == Project.PLATFORM_ANDROID && filter == Filter.ANDROID ||
                            it.platform == Project.PLATFORM_IOS && filter == Filter.IOS
                }
            })
            .onErrorReturnItem(listOf())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressSubject.onNext(true) }
            .doOnNext {
                progressSubject.onNext(false)
                emptySubject.onNext(it.isEmpty())
            }

}