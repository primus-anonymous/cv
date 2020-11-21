package com.neocaptainnemo.cv.ui.contacts.compose

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.compose.HALF_MARGIN
import com.neocaptainnemo.cv.ui.contacts.ContactSection
import com.neocaptainnemo.cv.ui.contacts.ContactsHeader
import kotlinx.coroutines.flow.Flow


@Composable
fun ContactsScreen(
        contactsFlow: Flow<List<Any>>,
        progressFlow: Flow<Boolean>,
        itemClicked: ContactItemClicked?,
) {

    val contactsList = contactsFlow.collectAsState(initial = listOf())

    val progress = progressFlow.collectAsState(initial = false)

    Column {

        TopAppBar(title = {
            Text(text = stringResource(id = R.string.title_contacts))
        })

        Box {

            ScrollableColumn {
                contactsList.value.map { item ->
                    when (item) {
                        is ContactsHeader -> {
                            Box(modifier = Modifier.padding(bottom = HALF_MARGIN)) {
                                ContactHeader(item)
                            }
                        }

                        is ContactSection -> {
                            ContactItem(item, itemClicked)
                        }
                    }
                }
            }

            if (progress.value) {
                LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


