package com.neocaptainnemo.cv.services

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics

internal class AnalyticsService(context: Context) : IAnalyticsService {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun log(event: AnalyticsEvent) = firebaseAnalytics.logEvent(event.eventValue, null)
}