package com.neocaptainnemo.cv.ui.projects.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.Filter
import com.neocaptainnemo.cv.ui.compose.cvColors

import com.neocaptainnemo.cv.ui.compose.defaultMargin

@Composable
fun MenuItem(
        @StringRes title: Int,
        selected: Boolean,
        filter: Filter,
        filterClicked: ((filter: Filter) -> Unit)?,
) {
    Row {
        Text(stringResource(id = title),
             textAlign = TextAlign.Justify,
             modifier = Modifier.weight(1.0f, true)
                     .padding(end = defaultMargin))

        RadioButton(modifier = Modifier,
                    selected = selected,
                    onClick = {
                        filterClicked?.invoke(filter)
                    })
    }
}

@Preview
@Composable
fun PreviewMenuItem() {
    MaterialTheme(colors = cvColors()) {
        MenuItem(
                R.string.action_all,
                true,
                Filter.ALL,
                null
        )
    }
}
