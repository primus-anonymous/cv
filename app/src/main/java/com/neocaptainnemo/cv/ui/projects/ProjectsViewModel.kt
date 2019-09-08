package com.neocaptainnemo.cv.ui.projects

import androidx.lifecycle.ViewModel
import com.neocaptainnemo.cv.model.Filter
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.IDataService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class ProjectsViewModel(private val dataService: IDataService) : ViewModel() {

    private val progressSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    private val emptySubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    private val filterSubject: BehaviorSubject<Filter> = BehaviorSubject.createDefault(Filter.ALL)

    var filter: Filter
        get() = filterSubject.value!!
        set(value) = filterSubject.onNext(value)

    val progress: Observable<Boolean> = progressSubject

    val empty: Observable<Boolean> = emptySubject

    val projects: Observable<List<Project>>
        get() = Observable.combineLatest(dataService.projects(), filterSubject,
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