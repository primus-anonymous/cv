package com.neocaptainnemo.cv.ui.contacts.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.ui.compose.*
import com.neocaptainnemo.cv.ui.contacts.ContactsHeader

private val IMG_HEIGHT = 200.dp

const val CONTACTS_HEADER_SEMANTICS_NAME = "ContactHeader_SEMANTICS_NAME"
const val CONTACTS_HEADER_SEMANTICS_PROF = "ContactHeader_SEMANTICS_PROF"

@Composable
fun ContactHeader(header: ContactsHeader) {
    Box(modifier = Modifier.height(IMG_HEIGHT)) {

        UrlImage(url = header.image,
                 modifier = Modifier.fillMaxHeight())

        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            Text(text = header.name,
                 modifier = Modifier.padding(DEFAULT_MARGIN, NO_MARGIN)
                         .semantics { testTag = CONTACTS_HEADER_SEMANTICS_NAME },
                 style = TextStyle.White16)

            Text(text = header.profession,
                 modifier =
                 Modifier
                         .padding(DEFAULT_MARGIN,
                                  SMALL_MARGIN,
                                  DEFAULT_MARGIN,
                                  DEFAULT_MARGIN)
                         .semantics { testTag = CONTACTS_HEADER_SEMANTICS_PROF },
                 style = TextStyle.White14)
        }
    }
}

@Preview
@Composable
fun PreviewContactHeader() {
    MaterialTheme(colors = CvColors) {
        ContactHeader(ContactsHeader(
                image = "https://images.freeimages.com/images/large-previews/996/easter-1399885.jpg",
                name = "Name",
                profession = "My occupation is rather dangerous business"
        ))
    }
}