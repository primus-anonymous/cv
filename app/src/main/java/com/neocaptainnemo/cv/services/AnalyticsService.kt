package com.neocaptainnemo.cv.services

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsService(context: Context) : IAnalyticsService {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun log(event: String) = firebaseAnalytics.logEvent(event, null)

    companion object {
        const val contactsClicked = "contactsClicked"
        const val projectsClicked = "projectsClicked"
        const val commonClicked = "commonClicked"
        const val phoneClicked = "phoneClicked"
        const val emailClicked = "emailClicked"
        const val gitHubClicked = "gitHubClicked"
        const val cvClicked = "cvClicked"
        const val projectSourceCodeClicked = "projectSourceCodeClicked"
        const val projectStoreClicked = "projectSoreClicked"
        const val projectShareClicked = "projectShareClicked"
        const val projectClicked = "projectClicked"
    }

}