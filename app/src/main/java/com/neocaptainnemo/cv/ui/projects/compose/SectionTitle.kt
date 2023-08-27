package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.compose.LightColors
import com.neocaptainnemo.cv.ui.compose.primary20

@Composable
fun SectionTitle(stingRes: Int) {
    Text(
        text = stringResource(id = stingRes),
        style = TextStyle.primary20(),
        modifier = Modifier.padding(
            horizontal = dimensionResource(id = R.dimen.app_default_margin),
            vertical = dimensionResource(id = R.dimen.app_small_margin)
        )
    )
}

@Preview
@Composable
fun PreviewSectionTitle() {
    MaterialTheme(LightColors) {
        SectionTitle(R.string.source_code)
    }
}
