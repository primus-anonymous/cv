package com.neocaptainnemo.cv.ui.common.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.parseAsHtml
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.toAnnotatedString
import com.neocaptainnemo.cv.ui.compose.LightColors
import com.neocaptainnemo.cv.ui.compose.primary20
import com.neocaptainnemo.cv.ui.compose.secondary14

@Composable
fun CommonSectionItem(
    title: String,
    description: String,
) {
    Surface(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.app_default_corner)),
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.app_small_margin))
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.app_default_margin))) {
            Text(
                text = title,
                style = TextStyle.primary20()
            )
            val htmlSpanned = description
                .parseAsHtml()
            Text(
                text = htmlSpanned.toAnnotatedString(),
                style = TextStyle.secondary14(),
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.app_half_margin))
            )
        }
    }
}

@Preview
@Composable
fun PreviewCommonSectionItem() {
    MaterialTheme(LightColors) {
        CommonSectionItem(
            title = "This is quite title",
            description = "Fardel may refer to: Shakespearian word meaning \"traveller's bundle\", as used in The Winter's Tale. Shakespearian word meaning \"burden\", as used in Hamlet's To be, or not to be speech. Scots word, also spelled \"Farl\", quadrant-shaped flatbread or cake."
        )
    }
}
