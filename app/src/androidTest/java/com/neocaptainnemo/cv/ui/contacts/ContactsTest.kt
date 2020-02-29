package com.neocaptainnemo.cv.ui.contacts

import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.BundleMatchers.hasEntry
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.neocaptainnemo.cv.MainActivity
import com.neocaptainnemo.cv.MockApp
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.matchers.RecyclerViewItemCountAssertion
import com.neocaptainnemo.cv.matchers.RecyclerViewMatcher
import com.neocaptainnemo.cv.model.Contacts
import com.neocaptainnemo.cv.services.AnalyticsEvent
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactsTest {

    @Rule
    @JvmField
    var activityRule: IntentsTestRule<MainActivity> = IntentsTestRule(
            MainActivity::class.java, true, false)

    val app: MockApp
        get() = getInstrumentation().targetContext.applicationContext as MockApp

    @Before
    fun setUp() {
        whenever(app.mockDataService.contacts()).thenReturn(flowOf(
                Contacts(
                        phone = "+7 907 76 54",
                        email = "email@email.com",
                        github = "http://www.github.com",
                        cvUrl = "http://cv.url.com",
                        name = "Master of Puppets",
                        profession = "Master"
                )
        ))
    }

    @Test
    fun phoneClicked() {

        activityRule.launchActivity(Intent())

        val expected = allOf(hasAction(Intent.ACTION_DIAL), hasData(Uri.fromParts("tel", "+7 907 76 54", null)))

        intending(expected).respondWith(Instrumentation.ActivityResult(0, null))

        onView(withId(R.id.contactsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        intended(expected)

        verify(app.mockAnalyticService).log(AnalyticsEvent.PHONE_CLICKED)
    }

    @Test
    fun emailClicked() {

        activityRule.launchActivity(Intent())


        val expected = allOf(hasAction(Intent.ACTION_CHOOSER), hasExtras(allOf(
                hasEntry(equalTo(Intent.EXTRA_INTENT), hasData(Uri.fromParts("mailto", "email@email.com", null))),
                hasEntry(equalTo(Intent.EXTRA_INTENT), hasExtra(Intent.EXTRA_SUBJECT, activityRule.activity.getString(R.string.mail_subject))
                ))))

        intending(expected).respondWith(Instrumentation.ActivityResult(0, null))

        onView(withId(R.id.contactsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        intended(expected)

        verify(app.mockAnalyticService).log(AnalyticsEvent.EMAIL_CLICKED)
    }


    @Test
    fun gitHubClicked() {

        activityRule.launchActivity(Intent())

        val expected = allOf(hasAction(Intent.ACTION_VIEW), hasData(Uri.parse("http://www.github.com")))

        intending(expected).respondWith(Instrumentation.ActivityResult(0, null))

        onView(withId(R.id.contactsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))

        intended(expected)

        verify(app.mockAnalyticService).log(AnalyticsEvent.GIT_HUB_CLICKED)
    }


    @Test
    fun downLoadCvClicked() {

        activityRule.launchActivity(Intent())

        val expected = allOf(hasAction(Intent.ACTION_VIEW), hasData(Uri.parse("http://cv.url.com")))

        intending(expected).respondWith(Instrumentation.ActivityResult(0, null))

        onView(withId(R.id.contactsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))

        intended(expected)

        verify(app.mockAnalyticService).log(AnalyticsEvent.CV_CLICKED)
    }

    @Test
    fun uiBindings() {

        activityRule.launchActivity(Intent())


        onView(RecyclerViewMatcher(R.id.contactsList).atPosition(0))
                .check(matches(hasDescendant(allOf(withId(R.id.profileName), withText("Master of Puppets")))))

        onView(RecyclerViewMatcher(R.id.contactsList).atPosition(0))
                .check(matches(hasDescendant(allOf(withId(R.id.profileProfession), withText("Master")))))


        onView(withId(R.id.contactsList)).check(RecyclerViewItemCountAssertion(5))
    }
}