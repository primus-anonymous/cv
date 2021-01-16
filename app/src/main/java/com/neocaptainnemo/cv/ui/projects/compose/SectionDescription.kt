package com.neocaptainnemo.cv.ui.projects.compose

import android.text.Spanned
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.core.text.toSpanned
import androidx.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.ui.compose.*

@Composable
fun SectionDescription(string: Spanned) {
    Text(text = AnnotatedString(string.toString()),
         style = TextStyle.secondary14(),
         modifier = Modifier.padding(horizontal = defaultMargin,
                                     vertical = smallMargin)
    )
}

@Preview
@Composable
fun PreviewSectionDescription() {
    MaterialTheme(colors = cvColors()) {
        SectionDescription("This is example spanned string".toSpanned())
    }
}
