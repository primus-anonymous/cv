package com.neocaptainnemo.cv.ui.common.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.text.parseAsHtml
import androidx.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.ui.compose.*

@Composable
fun CommonSectionItem(
        title: String,
        description: String,
) {
    Surface(shape = RoundedCornerShape(DEFAULT_CORNER),
            elevation = 4.dp,
            modifier = Modifier.padding(SMALL_MARGIN)
                    .fillMaxWidth()) {
        Column(modifier = Modifier.padding(DEFAULT_MARGIN)) {
            Text(text = title,
                 style = TextStyle.Primary20)
            val htmlSpanned = description
                    .parseAsHtml()
            Text(text = htmlSpanned.toString(),
                 style = TextStyle.Secondary14,
                 modifier = Modifier.padding(top = HALF_MARGIN))
        }
    }
}

@Preview
@Composable
fun PreviewCommonSectionItem() {
    MaterialTheme(colors = CvColors) {
        CommonSectionItem(
                title = "This is quite title",
                description = "Fardel may refer to: Shakespearian word meaning \"traveller's bundle\", as used in The Winter's Tale. Shakespearian word meaning \"burden\", as used in Hamlet's To be, or not to be speech. Scots word, also spelled \"Farl\", quadrant-shaped flatbread or cake."
        )
    }
}
