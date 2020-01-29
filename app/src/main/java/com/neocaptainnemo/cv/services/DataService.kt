package com.neocaptainnemo.cv.services

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.neocaptainnemo.cv.model.*
import io.reactivex.Observable

internal class DataService(private val localeService: ILocaleService) : IDataService {

    init {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }


    override fun projects(): Observable<List<Project>> =
            databaseRequest<ProjectsResponse>()
                    .map {

                        val strings = it.resources[localeService.locale]

                        it.projects.map { project ->
                            project.apply {
                                name = strings?.get(name)
                                description = strings?.get(description)
                                duties = strings?.get(duties)
                            }
                        }
                    }

    override fun commons(): Observable<List<CommonSection>> =
            databaseRequest<CommonResponse>()
                    .map {

                        val strings = it.resources[localeService.locale]

                        it.common?.sections?.map { section ->
                            section.apply {
                                title = strings?.get(title)
                                description = strings?.get(description)
                            }
                        } ?: listOf()
                    }

    override fun contacts(): Observable<Contacts> =
            databaseRequest<ContactsResponse>()
                    .map {

                        val strings = it.resources[localeService.locale]

                        return@map it.contacts?.apply {
                            cvUrl = strings?.get(cvUrl)
                            name = strings?.get(name)
                            profession = strings?.get(profession)

                        } ?: Contacts()

                    }

    private inline fun <reified T> databaseRequest(): Observable<T> =
            Observable.create<T> {

                val valueListener = object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) = it.onError(error.toException())

                    override fun onDataChange(snapshot: DataSnapshot) {

                        val value = snapshot.getValue(T::class.java)

                        if (value != null) {
                            it.onNext(value)
                        } else {
                            it.onError(NullPointerException())
                        }
                    }

                }

                FirebaseDatabase.getInstance().reference.addValueEventListener(valueListener)

                it.setCancellable {
                    FirebaseDatabase.getInstance().reference.removeEventListener(valueListener)
                }
            }
}