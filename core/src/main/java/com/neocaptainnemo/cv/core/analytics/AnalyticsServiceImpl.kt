package com.neocaptainnemo.cv.core.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics

internal class AnalyticsServiceImpl(context: Context) : AnalyticsService {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun log(event: AnalyticsEvent) = firebaseAnalytics.logEvent(event.eventValue,
                                                                         null)
}