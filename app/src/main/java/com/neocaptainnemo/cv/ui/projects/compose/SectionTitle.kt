package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.compose.CvColors
import com.neocaptainnemo.cv.ui.compose.DEFAULT_MARGIN
import com.neocaptainnemo.cv.ui.compose.Primary20
import com.neocaptainnemo.cv.ui.compose.SMALL_MARGIN

@Composable
fun SectionTitle(stingRes: Int) {
    Text(text = stringResource(id = stingRes),
         style = TextStyle.Primary20,
         modifier = Modifier.padding(horizontal = DEFAULT_MARGIN,
                                     vertical = SMALL_MARGIN)
    )
}

@Preview
@Composable
fun PreviewSectionTitle() {
    MaterialTheme(colors = CvColors) {
        SectionTitle(R.string.source_code)
    }
}
