package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.compose.defaultMargin
import com.neocaptainnemo.cv.ui.compose.primary20
import com.neocaptainnemo.cv.ui.compose.cvColors
import com.neocaptainnemo.cv.ui.compose.smallMargin

@Composable
fun SectionTitle(stingRes: Int) {
    Text(text = stringResource(id = stingRes),
         style = TextStyle.primary20(),
         modifier = Modifier.padding(horizontal = defaultMargin,
                                     vertical = smallMargin)
    )
}

@Preview
@Composable
fun PreviewSectionTitle() {
    MaterialTheme(colors = cvColors()) {
        SectionTitle(R.string.source_code)
    }
}
