package com.neocaptainnemo.cv.services

import com.neocaptainnemo.cv.model.CommonSection
import com.neocaptainnemo.cv.model.Contacts
import com.neocaptainnemo.cv.model.Project
import io.reactivex.Observable

interface IDataService {

    fun projects(): Observable<List<Project>>

    fun commons(): Observable<List<CommonSection>>

    fun contacts(): Observable<Contacts>
}