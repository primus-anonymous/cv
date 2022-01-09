package com.neocaptainnemo.cv.core.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.neocaptainnemo.cv.core.locale.LocaleService
import com.neocaptainnemo.cv.core.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class DataServiceImpl @Inject constructor(private val localeService: LocaleService) : DataService {

    init {
        FirebaseDatabase.getInstance()
                .setPersistenceEnabled(true)
    }

    override fun projects(): Flow<List<Project>> = coroutineRequest<ProjectsResponse>().map {
        val strings = it.resources[localeService.locale]

        it.projects.map { project ->
            project.apply {
                name = strings?.get(name)
                description = strings?.get(description)
                duties = strings?.get(duties)
            }
        }
    }

    override fun project(id: String): Flow<Project> = projects()
        .map { it.first { it.id == id } }

    override fun contacts(): Flow<Contacts> = coroutineRequest<ContactsResponse>().map {
        val strings = it.resources[localeService.locale]

        return@map it.contacts?.apply {
            cvUrl = strings?.get(cvUrl)
            name = strings?.get(name)
            profession = strings?.get(profession)

        } ?: Contacts()

    }

    override fun commons(): Flow<List<CommonSection>> = coroutineRequest<CommonResponse>().map {
        val strings = it.resources[localeService.locale]

        it.common?.sections?.map { section ->
            section.apply {
                title = strings?.get(title)
                description = strings?.get(description)
            }
        } ?: listOf()
    }

    private inline fun <reified T> coroutineRequest(): Flow<T> = callbackFlow {

        val valueListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val value = snapshot.getValue(T::class.java)

                value?.run {
                    trySend(this)
                } ?: close(NullPointerException())
            }
        }

        FirebaseDatabase.getInstance().reference.addValueEventListener(valueListener)

        awaitClose {
            FirebaseDatabase.getInstance().reference.removeEventListener(valueListener)
        }
    }
}