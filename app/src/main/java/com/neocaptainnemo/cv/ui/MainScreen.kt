package com.neocaptainnemo.cv.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.compose.cvColors

enum class Tab(
        @StringRes val title: Int,
        @DrawableRes val icon: Int,
) {
    CONTACTS(R.string.title_contacts,
             R.drawable.ic_call_black_24px),
    PROJECTS(R.string.project,
             R.drawable.ic_shop_black_24px),
    COMMON(R.string.action_common,
           R.drawable.ic_inbox_black_24px)
}


data class MainScreenPage(
        val tab: Tab,
        val content: @Composable RowScope.() -> Unit,
)

@Composable
fun MainScreen(
        pages: List<MainScreenPage>,
        currentPage: MutableState<MainScreenPage>,
        tabSelected: (tab: Tab) -> Unit,
) {

    MaterialTheme(colors = cvColors()) {

        Column {

            Row(modifier = Modifier.weight(1.0f, true)) {
                currentPage.value.content(this)
            }

            BottomNavigation {
                pages.map { page ->
                    BottomNavigationItem(
                            icon = {
                                Image(vectorResource(page.tab.icon))
                            },
                            label = {
                                Text(stringResource(page.tab.title))
                            },
                            selected = page.tab == currentPage.value.tab,
                            onClick = {
                                if (page.tab != currentPage.value.tab) {
                                    currentPage.value = page
                                }

                                tabSelected.invoke(page.tab)
                            })
                }
            }
        }
    }
}

