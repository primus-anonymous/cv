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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.parseAsHtml
import com.neocaptainnemo.cv.ui.compose.cvColors
import com.neocaptainnemo.cv.ui.compose.defaultCorner
import com.neocaptainnemo.cv.ui.compose.defaultMargin
import com.neocaptainnemo.cv.ui.compose.halfMargin
import com.neocaptainnemo.cv.ui.compose.primary20
import com.neocaptainnemo.cv.ui.compose.secondary14
import com.neocaptainnemo.cv.ui.compose.smallMargin

@Composable
fun CommonSectionItem(
    title: String,
    description: String,
) {
    Surface(
        shape = RoundedCornerShape(defaultCorner),
        elevation = 4.dp,
        modifier = Modifier.padding(smallMargin)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(defaultMargin)) {
            Text(
                text = title,
                style = TextStyle.primary20()
            )
            val htmlSpanned = description
                .parseAsHtml()
            Text(
                text = htmlSpanned.toString(),
                style = TextStyle.secondary14(),
                modifier = Modifier.padding(top = halfMargin)
            )
        }
    }
}

@Preview
@Composable
fun PreviewCommonSectionItem() {
    MaterialTheme(colors = cvColors()) {
        CommonSectionItem(
            title = "This is quite title",
            description = "Fardel may refer to: Shakespearian word meaning \"traveller's bundle\", as used in The Winter's Tale. Shakespearian word meaning \"burden\", as used in Hamlet's To be, or not to be speech. Scots word, also spelled \"Farl\", quadrant-shaped flatbread or cake."
        )
    }
}
