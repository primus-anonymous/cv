package com.neocaptainnemo.cv.ui.contacts.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.analytics.AnalyticsEvent
import com.neocaptainnemo.cv.core.analytics.AnalyticsService
import com.neocaptainnemo.cv.ui.compose.halfMargin
import com.neocaptainnemo.cv.ui.contacts.ContactSection
import com.neocaptainnemo.cv.ui.contacts.ContactType
import com.neocaptainnemo.cv.ui.contacts.ContactsHeader
import com.neocaptainnemo.cv.ui.contacts.ContactsViewModel

@Composable
fun ContactsScreen(
    analyticsService: AnalyticsService,
    vm: ContactsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val contactsList by remember(vm) { vm.contacts() }.collectAsState(initial = listOf())

    val progress by remember(vm) { vm.progress }.collectAsState(initial = false)

    fun onContactCLicked(contact: ContactSection) {
        when (contact.type) {
            ContactType.EMAIL -> {
                Intent(
                    Intent.ACTION_SENDTO,
                    Uri.fromParts(
                        "mailto",
                        contact.value,
                        null
                    )
                ).apply {
                    putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.mail_subject))
                }
                    .also {
                        context.startActivity(
                            Intent.createChooser(it, context.getString(R.string.mail_chooser))
                        )
                    }

                analyticsService.log(AnalyticsEvent.EMAIL_CLICKED)
            }

            ContactType.PHONE -> {
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.fromParts(
                        "tel",
                        contact.value,
                        null
                    )
                ).also {
                    context.startActivity(it)
                }

                analyticsService.log(AnalyticsEvent.PHONE_CLICKED)
            }

            ContactType.GIT_HUB -> {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(contact.value)
                ).also {
                    context.startActivity(it)
                }

                analyticsService.log(AnalyticsEvent.GIT_HUB_CLICKED)
            }

            ContactType.CV -> {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(contact.value)
                ).also {
                    context.startActivity(it)
                }

                analyticsService.log(AnalyticsEvent.CV_CLICKED)
            }

            ContactType.TELEGRAM -> {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://telegram.me/${contact.value}")
                ).also {
                    context.startActivity(it)
                }

                analyticsService.log(AnalyticsEvent.TELEGRAM_CLICKED)
            }
        }
    }

    Column {

        TopAppBar(title = {
            Text(text = stringResource(id = R.string.title_contacts))
        })

        Box {

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                contactsList.map { item ->
                    when (item) {
                        is ContactsHeader -> {
                            Box(modifier = Modifier.padding(bottom = halfMargin)) {
                                ContactHeader(item)
                            }
                        }

                        is ContactSection -> {
                            ContactItem(item, ::onContactCLicked)
                        }
                    }
                }
            }

            if (progress) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
