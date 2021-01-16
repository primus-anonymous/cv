package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.Filter
import com.neocaptainnemo.cv.core.model.Project
import com.neocaptainnemo.cv.ui.compose.halfMargin
import kotlinx.coroutines.flow.Flow

typealias ProjectItemClicked = ((project: Project) -> Unit)

typealias FilterItemClicked = ((filter: Filter) -> Unit)

private const val columns = 2

@Composable
fun ProjectsScreen(
        projectsFlow: Flow<List<Project>>,
        progressFlow: Flow<Boolean>,
        itemClicked: ProjectItemClicked?,
        filterSelected: FilterItemClicked?,
        getFilter: (() -> Filter)?,
) {

    val projectsList: List<List<Project>> = projectsFlow.collectAsState(initial = listOf())
            .value
            .chunked(columns)

    val progress = progressFlow.collectAsState(initial = false)

    val menuOpenedState = remember { mutableStateOf(false) }

    fun filterSelected(filter: Filter) {
        filterSelected?.invoke(filter)
        menuOpenedState.value = false
    }

    Column {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.projects))
        },
                  actions = {
                      Box(modifier = Modifier.padding(end = halfMargin)
                              .clickable(onClick = {
                                  menuOpenedState.value = menuOpenedState.value.not()
                              })) {
                          Image(vectorResource(id = R.drawable.ic_baseline_filter_list_24))

                          if (menuOpenedState.value) {
                              DropdownMenu(onDismissRequest = {
                                  menuOpenedState.value = false
                              },
                                           expanded = menuOpenedState.value,
                                           toggle = {

                                           }) {
                                  Surface(
                                          shape = RoundedCornerShape(4.dp),
                                          elevation = 16.dp
                                  )
                                  {
                                      Column(modifier = Modifier.padding(10.dp)) {
                                          MenuItem(R.string.action_all,
                                                   getFilter?.invoke() == Filter.ALL,
                                                   Filter.ALL,
                                                   ::filterSelected)
                                          MenuItem(R.string.action_android,
                                                   getFilter?.invoke() == Filter.ANDROID,
                                                   Filter.ANDROID,
                                                   ::filterSelected)
                                          MenuItem(R.string.action_ios,
                                                   getFilter?.invoke() == Filter.IOS,
                                                   Filter.IOS,
                                                   ::filterSelected)
                                      }
                                  }
                              }
                          }
                      }
                  })

        Box {
            LazyColumnFor(items = projectsList) {
                GridColumn(columns = columns) {
                    it.map { proj ->
                        ProjectItem(project = proj,
                                    itemClicked = itemClicked)
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

