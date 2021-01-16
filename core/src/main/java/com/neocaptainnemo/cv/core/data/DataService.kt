package com.neocaptainnemo.cv.core.data

import com.neocaptainnemo.cv.core.model.CommonSection
import com.neocaptainnemo.cv.core.model.Contacts
import com.neocaptainnemo.cv.core.model.Project
import kotlinx.coroutines.flow.Flow

interface DataService {

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