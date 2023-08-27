package com.neocaptainnemo.cv.ui.projects.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.Filter
import com.neocaptainnemo.cv.ui.compose.LightColors

@Composable
fun MenuItem(
    @StringRes title: Int,
    selected: Boolean,
    filter: Filter,
    filterClicked: ((filter: Filter) -> Unit)?,
) {
    Row {
        Text(
            stringResource(id = title),
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .weight(1.0f, true)
                .padding(end = dimensionResource(id = R.dimen.app_default_margin))
        )

        RadioButton(
            modifier = Modifier,
            selected = selected,
            onClick = {
                filterClicked?.invoke(filter)
            }
        )
    }
}

@Preview
@Composable
fun PreviewMenuItem() {
    MaterialTheme(LightColors) {
        MenuItem(
            R.string.action_all,
            true,
            Filter.ALL,
            null
        )
    }
}
