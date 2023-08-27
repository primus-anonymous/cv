package com.neocaptainnemo.cv.ui.projects.compose

import android.text.Spanned
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.toSpanned
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.toAnnotatedString
import com.neocaptainnemo.cv.ui.compose.LightColors
import com.neocaptainnemo.cv.ui.compose.secondary14

@Composable
fun SectionDescription(string: Spanned) {
    Text(
        text = string.toAnnotatedString(),
        style = TextStyle.secondary14(),
        modifier = Modifier.padding(
            horizontal = dimensionResource(id = R.dimen.app_default_margin),
            vertical = dimensionResource(id = R.dimen.app_small_margin)
        )
    )
}

@Preview
@Composable
fun PreviewSectionDescription() {
    MaterialTheme(LightColors) {
        SectionDescription("This is example spanned string".toSpanned())
    }
}
