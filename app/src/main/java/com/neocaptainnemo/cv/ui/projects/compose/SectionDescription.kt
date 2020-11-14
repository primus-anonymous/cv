package com.neocaptainnemo.cv.ui.projects.compose

import android.text.Spanned
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.text.toSpanned
import androidx.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.ui.compose.CvColors
import com.neocaptainnemo.cv.ui.compose.DEFAULT_MARGIN
import com.neocaptainnemo.cv.ui.compose.SMALL_MARGIN
import com.neocaptainnemo.cv.ui.compose.Secondary14

@Composable
fun SectionDescription(string: Spanned) {
    Text(text = AnnotatedString(string.toString()),
         style = TextStyle.Secondary14,
         modifier = Modifier.padding(horizontal = DEFAULT_MARGIN,
                                     vertical = SMALL_MARGIN)
    )
}

@Preview
@Composable
fun PreviewSectionDescription() {
    MaterialTheme(colors = CvColors) {
        SectionDescription("This is example spanned string".toSpanned())
    }
}
