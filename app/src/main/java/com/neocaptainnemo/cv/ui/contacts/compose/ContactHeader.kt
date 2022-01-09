package com.neocaptainnemo.cv.ui.contacts.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neocaptainnemo.cv.ui.compose.UrlImage
import com.neocaptainnemo.cv.ui.compose.White14
import com.neocaptainnemo.cv.ui.compose.White16
import com.neocaptainnemo.cv.ui.compose.cvColors
import com.neocaptainnemo.cv.ui.compose.defaultMargin
import com.neocaptainnemo.cv.ui.compose.noMargin
import com.neocaptainnemo.cv.ui.compose.smallMargin
import com.neocaptainnemo.cv.ui.contacts.ContactsHeader

private val imageHeight = 200.dp

const val CONTACTS_HEADER_SEMANTICS_NAME = "ContactHeader_SEMANTICS_NAME"
const val CONTACTS_HEADER_SEMANTICS_PROF = "ContactHeader_SEMANTICS_PROF"

@Composable
fun ContactHeader(header: ContactsHeader) {
    Box(modifier = Modifier.height(imageHeight)) {

        UrlImage(
            url = header.image,
            modifier = Modifier.fillMaxHeight()
                .fillMaxWidth()
        )

        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            Text(
                text = header.name,
                modifier = Modifier.padding(defaultMargin, noMargin)
                    .semantics { testTag = CONTACTS_HEADER_SEMANTICS_NAME },
                style = TextStyle.White16
            )

            Text(
                text = header.profession,
                modifier =
                Modifier
                    .padding(
                        defaultMargin,
                        smallMargin,
                        defaultMargin,
                        defaultMargin
                    )
                    .semantics { testTag = CONTACTS_HEADER_SEMANTICS_PROF },
                style = TextStyle.White14
            )
        }
    }
}

@Preview
@Composable
fun PreviewContactHeader() {
    MaterialTheme(colors = cvColors()) {
        ContactHeader(
            ContactsHeader(
                image = "https://images.freeimages.com/images/large-previews/996/easter-1399885.jpg",
                name = "Name",
                profession = "My occupation is rather dangerous business"
            )
        )
    }
}
