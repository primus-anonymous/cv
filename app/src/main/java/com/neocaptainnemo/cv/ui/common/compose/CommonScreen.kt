package com.neocaptainnemo.cv.ui.common.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.common.CommonViewModel

@ExperimentalMaterial3Api
@Composable
fun CommonScreen(
    vm: CommonViewModel = hiltViewModel()
) {

    val contactsList by remember(vm) { vm.commons() }.collectAsState(initial = listOf())

    val progress by remember(vm) { vm.progress }.collectAsState(initial = false)

    Column {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.action_common))
        })

        Box {
            LazyColumn {
                items(contactsList.size) { index ->
                    val item = contactsList[index]
                    CommonSectionItem(
                        title = item.title.orEmpty(),
                        description = item.description.orEmpty()
                    )
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
