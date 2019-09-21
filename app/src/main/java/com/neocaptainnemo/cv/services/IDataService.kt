package com.neocaptainnemo.cv.services

import com.neocaptainnemo.cv.model.CommonSection
import com.neocaptainnemo.cv.model.Contacts
import com.neocaptainnemo.cv.model.Project
import io.reactivex.Observable

interface IDataService {

    /**
     * Provides the list of projects.
     */
    fun projects(): Observable<List<Project>>

    /**
     * Provides common cv information.
     */
    fun commons(): Observable<List<CommonSection>>

    /**
     * Provides the list of contacts.
     */
    fun contacts(): Observable<Contacts>
}