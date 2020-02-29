package com.neocaptainnemo.cv.services

import com.neocaptainnemo.cv.model.CommonSection
import com.neocaptainnemo.cv.model.Contacts
import com.neocaptainnemo.cv.model.Project
import kotlinx.coroutines.flow.Flow

interface IDataService {

    /**
     * Provides the list of projects.
     */

    fun projects(): Flow<List<Project>>

    /**
     * Provides the list of contacts.
     */
    fun contacts(): Flow<Contacts>

    /**
     * Provides common cv information.
     */

    fun commons(): Flow<List<CommonSection>>

}