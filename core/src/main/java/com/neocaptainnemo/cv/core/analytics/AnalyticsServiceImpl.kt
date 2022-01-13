package com.neocaptainnemo.cv.core.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class AnalyticsServiceImpl @Inject constructor(@ApplicationContext context: Context) : AnalyticsService {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun log(event: AnalyticsEvent) = firebaseAnalytics.logEvent(event.eventValue,
                                                                         null)
}