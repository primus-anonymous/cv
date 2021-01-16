package com.neocaptainnemo.cv.ui.common.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.CommonSection
import kotlinx.coroutines.flow.Flow

@Composable
fun CommonScreen(
        contactsFlow: Flow<List<CommonSection>>,
        progressFlow: Flow<Boolean>,
) {

    val contactsList = contactsFlow.collectAsState(initial = listOf())

    val progress = progressFlow.collectAsState(initial = false)

    Column {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.action_common))
        })

        Box {
            LazyColumn {
                items(items = contactsList.value, itemContent = {
                    CommonSectionItem(
                            title = it.title.orEmpty(),
                            description = it.description.orEmpty())
                })
            }

            if (progress.value) {
                LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


