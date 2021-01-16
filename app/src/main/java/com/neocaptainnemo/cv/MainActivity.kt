package com.neocaptainnemo.cv

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.setContent
import com.neocaptainnemo.cv.core.analytics.AnalyticsEvent
import com.neocaptainnemo.cv.core.analytics.AnalyticsService
import com.neocaptainnemo.cv.ui.MainScreen
import com.neocaptainnemo.cv.ui.MainScreenPage
import com.neocaptainnemo.cv.ui.Tab
import com.neocaptainnemo.cv.ui.common.CommonViewModel
import com.neocaptainnemo.cv.ui.common.compose.CommonScreen
import com.neocaptainnemo.cv.ui.contacts.ContactSection
import com.neocaptainnemo.cv.ui.contacts.ContactType
import com.neocaptainnemo.cv.ui.contacts.ContactsViewModel
import com.neocaptainnemo.cv.ui.contacts.compose.ContactsScreen
import com.neocaptainnemo.cv.ui.projects.ProjectDetailsActivity
import com.neocaptainnemo.cv.ui.projects.ProjectsViewModel
import com.neocaptainnemo.cv.ui.projects.compose.ProjectsScreen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val contactsVm: ContactsViewModel by viewModel()

    private val projectsVm: ProjectsViewModel by viewModel()

    private val commonsViewModel: CommonViewModel by viewModel()

    private val analyticsService: AnalyticsService by inject()

    private val pages = listOf(
            MainScreenPage(Tab.CONTACTS)
            {
                ContactsScreen(contactsFlow = contactsVm.contacts(),
                               progressFlow = contactsVm.progress,
                               itemClicked = ::onContactCLicked)
            },
            MainScreenPage(Tab.PROJECTS)
            {
                ProjectsScreen(projectsFlow = projectsVm.projects(),
                               progressFlow = projectsVm.progress,
                               itemClicked = {
                                   ProjectDetailsActivity.show(this@MainActivity, it)

                                   analyticsService.log(AnalyticsEvent.PROJECT_DETAILS_CLICKED)
                               },
                               filterSelected = {
                                   projectsVm.filter = it
                               },
                               getFilter = {
                                   projectsVm.filter
                               }
                )
            },
            MainScreenPage(Tab.COMMON) {
                CommonScreen(commonsViewModel.commons(),
                             commonsViewModel.progress)
            }
    )

    private val currentPageState = mutableStateOf(pages.first { it.tab == Tab.CONTACTS })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(
                    pages,
                    currentPageState) {

                when (it) {
                    Tab.CONTACTS -> AnalyticsEvent.CONTACTS_CLICKED
                    Tab.PROJECTS -> AnalyticsEvent.PROJECTS_CLICKED
                    Tab.COMMON -> AnalyticsEvent.COMMON_CLICKED
                }.also { event ->
                    analyticsService.log(event)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (currentPageState.value.tab != Tab.CONTACTS) {
            currentPageState.value = pages.first { it.tab == Tab.CONTACTS }
        } else {
            super.onBackPressed()
        }
    }

    private fun onContactCLicked(contact: ContactSection) {
        when (contact.type) {
            ContactType.EMAIL -> {
                Intent(Intent.ACTION_SENDTO,
                       Uri.fromParts(
                               "mailto",
                               contact.value,
                               null)).apply {
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
                }
                        .also {
                            startActivity(
                                    Intent.createChooser(it, getString(R.string.mail_chooser)))
                        }

                analyticsService.log(AnalyticsEvent.EMAIL_CLICKED)
            }

            ContactType.PHONE -> {
                Intent(Intent.ACTION_DIAL,
                       Uri.fromParts("tel",
                                     contact.value,
                                     null)).also {
                    startActivity(it)
                }

                analyticsService.log(AnalyticsEvent.PHONE_CLICKED)
            }

            ContactType.GIT_HUB -> {
                Intent(Intent.ACTION_VIEW,
                       Uri.parse(contact.value)).also {
                    startActivity(it)
                }

                analyticsService.log(AnalyticsEvent.GIT_HUB_CLICKED)
            }

            ContactType.CV -> {
                Intent(Intent.ACTION_VIEW,
                       Uri.parse(contact.value)).also {
                    startActivity(it)
                }

                analyticsService.log(AnalyticsEvent.CV_CLICKED)
            }

            ContactType.TELEGRAM -> {
                Intent(Intent.ACTION_VIEW,
                       Uri.parse("https://telegram.me/${contact.value}")).also {
                    startActivity(it)
                }

                analyticsService.log(AnalyticsEvent.TELEGRAM_CLICKED)
            }
        }
    }
}
