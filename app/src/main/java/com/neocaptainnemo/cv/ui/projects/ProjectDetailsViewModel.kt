package com.neocaptainnemo.cv.ui.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.spanned
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ProjectDetailsViewModel(private val app: Application, private val project: Project) : AndroidViewModel(app) {

    private val projectSubj = BehaviorSubject.createDefault(project)

    val shareUrl: String
        get() =
            StringBuilder().apply {
                append(app.getString(R.string.project))
                append(' ')
                append(project.name)

                if (project.storeUrl.isNullOrEmpty().not()) {
                    append(' ')
                    append(project.storeUrl)
                }

                if (project.gitHub.isNullOrEmpty().not()) {
                    append(' ')
                    append(app.getString(R.string.code))
                    append(' ')
                    append(project.gitHub)
                }

            }.toString()

    val gitHubUrl: String?
        get() = project.gitHub

    val storeUrl: String?
        get() = project.storeUrl

    val storeVisibility: Observable<Boolean> = projectSubj.map { it.storeUrl.isNullOrBlank().not() }

    val gitHubVisibility: Observable<Boolean> = projectSubj.map { it.gitHub.isNullOrBlank().not() }

    val platformImage: Observable<Int> = projectSubj.map {
        if (project.platform == Project.PLATFORM_ANDROID) {
            R.drawable.ic_android
        } else {
            R.drawable.ic_apple
        }
    }

    val webPic: Observable<String> = projectSubj.map { it.webPic.orEmpty() }

    val coverPic: Observable<String> = projectSubj.map { it.coverPic.orEmpty() }

    val projectName: Observable<String> = projectSubj.map { it.name.orEmpty() }

    val stack: Observable<String> = projectSubj.map { it.stack.orEmpty() }

    val company: Observable<String> = projectSubj.map { it.vendor.orEmpty() }

    val duties: Observable<String> = projectSubj.map { it.duties.orEmpty() }

    val detailsDescription: Observable<String> = projectSubj.map { it.description.orEmpty() }

    val sourceCode: Observable<String> = projectSubj.map { it.gitHub.orEmpty() }

}