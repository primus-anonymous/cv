package com.neocaptainnemo.cv.ui.projects.compose

import android.text.Spanned
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.toSpanned
import com.neocaptainnemo.cv.toAnnotatedString
import com.neocaptainnemo.cv.ui.compose.cvColors
import com.neocaptainnemo.cv.ui.compose.defaultMargin
import com.neocaptainnemo.cv.ui.compose.secondary14
import com.neocaptainnemo.cv.ui.compose.smallMargin

@Composable
fun SectionDescription(string: Spanned) {
    Text(
        text = string.toAnnotatedString(),
        style = TextStyle.secondary14(),
        modifier = Modifier.padding(
            horizontal = defaultMargin,
            vertical = smallMargin
        )
    )
}

@Preview
@Composable
fun PreviewSectionDescription() {
    MaterialTheme(colors = cvColors()) {
        SectionDescription("This is example spanned string".toSpanned())
    }
}
