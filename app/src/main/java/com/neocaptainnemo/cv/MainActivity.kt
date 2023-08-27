package com.neocaptainnemo.cv

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.neocaptainnemo.cv.core.analytics.AnalyticsEvent
import com.neocaptainnemo.cv.core.analytics.AnalyticsService
import com.neocaptainnemo.cv.ui.Tab
import com.neocaptainnemo.cv.ui.common.compose.CommonScreen
import com.neocaptainnemo.cv.ui.compose.DarkColors
import com.neocaptainnemo.cv.ui.compose.LightColors
import com.neocaptainnemo.cv.ui.contacts.compose.ContactsScreen
import com.neocaptainnemo.cv.ui.projects.compose.ProjectDetailsScreen
import com.neocaptainnemo.cv.ui.projects.compose.ProjectsScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var analyticsService: AnalyticsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            MaterialTheme(if (isSystemInDarkTheme()) DarkColors else LightColors) {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            Tab.values().forEach { screen ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = screen.icon),
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(stringResource(screen.title)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }

                                        when (screen) {
                                            Tab.CONTACTS -> AnalyticsEvent.CONTACTS_CLICKED
                                            Tab.PROJECTS -> AnalyticsEvent.PROJECTS_CLICKED
                                            Tab.COMMON -> AnalyticsEvent.COMMON_CLICKED
                                        }.also { event ->
                                            analyticsService.log(event)
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Tab.CONTACTS.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Tab.CONTACTS.route) {
                            ContactsScreen(analyticsService)
                        }
                        composable(Tab.PROJECTS.route) {
                            ProjectsScreen(
                                itemClicked = {
                                    navController.navigate("details/${it.id}")

                                    analyticsService.log(AnalyticsEvent.PROJECT_DETAILS_CLICKED)
                                }
                            )
                        }
                        composable(Tab.COMMON.route) {
                            CommonScreen()
                        }

                        composable(
                            "details/{projectId}",
                            arguments = listOf(
                                navArgument("projectId") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            ProjectDetailsScreen(
                                projectId = it.arguments?.getString("projectId").orEmpty(),
                                analyticsService,
                            ) {
                                onBackPressed()
                            }
                        }
                    }
                }
            }
        }
    }
}
