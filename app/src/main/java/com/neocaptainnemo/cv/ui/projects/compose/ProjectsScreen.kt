package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.Filter
import com.neocaptainnemo.cv.core.model.Project
import com.neocaptainnemo.cv.ui.projects.ProjectsViewModel

typealias ProjectItemClicked = ((project: Project) -> Unit)

@ExperimentalMaterial3Api
@Composable
fun ProjectsScreen(
    vm: ProjectsViewModel = hiltViewModel(),
    itemClicked: ProjectItemClicked?,
) {

    val projectsList: List<Project> by remember { vm.projects() }.collectAsState(initial = listOf())

    val progress by remember(vm) { vm.progress }.collectAsState(initial = false)

    val menuOpenedState = remember { mutableStateOf(false) }

    fun filterSelected(filter: Filter) {
        vm.filter = filter
        menuOpenedState.value = false
    }

    Column {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.projects))
            },
            actions = {
                Box(
                    modifier = Modifier
                        .padding(end = dimensionResource(id = R.dimen.app_half_margin))
                        .clickable(onClick = {
                            menuOpenedState.value = menuOpenedState.value.not()
                        })
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_filters),
                        contentDescription = null
                    )

                    if (menuOpenedState.value) {
                        DropdownMenu(
                            onDismissRequest = {
                                menuOpenedState.value = false
                            },
                            expanded = menuOpenedState.value,
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                shadowElevation = 16.dp
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    MenuItem(
                                        R.string.action_all,
                                        vm.filter == Filter.ALL,
                                        Filter.ALL,
                                        ::filterSelected
                                    )
                                    MenuItem(
                                        R.string.action_android,
                                        vm.filter == Filter.ANDROID,
                                        Filter.ANDROID,
                                        ::filterSelected
                                    )
                                    MenuItem(
                                        R.string.action_ios,
                                        vm.filter == Filter.IOS,
                                        Filter.IOS,
                                        ::filterSelected
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )

        Box {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp)
            ) {
                items(projectsList) { proj ->
                    ProjectItem(
                        project = proj,
                        itemClicked = itemClicked
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
