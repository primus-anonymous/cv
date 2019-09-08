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

                        it.projects.forEach { project ->
                            project.name = strings?.get(project.nameKey)
                            project.description = strings?.get(project.descriptionKey)
                            project.duties = strings?.get(project.dutiesKey)

                        }

                        it.projects
                    }

    override fun commons(): Observable<List<CommonSection>> =
            databaseRequest<CommonResponse>()
                    .map {

                        if (it.common == null) {
                            return@map listOf<CommonSection>()
                        }

                        val strings = it.resources[localeService.locale]

                        it.common?.sections?.forEach { section ->
                            section.title = strings?.get(section.titleKey)
                            section.description = strings?.get(section.descriptionKey)

                        }

                        it.common?.sections
                    }

    override fun contacts(): Observable<Contacts> =
            databaseRequest<ContactsResponse>()
                    .map {

                        val strings = it.resources[localeService.locale]

                        val contacts = it.contacts ?: return@map Contacts()

                        contacts.cv = strings?.get(contacts.cvKey)
                        contacts.name = strings?.get(contacts.nameKey)
                        contacts.profession = strings?.get(contacts.professionKey)

                        return@map contacts

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