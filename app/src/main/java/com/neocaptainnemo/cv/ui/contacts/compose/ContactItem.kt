package com.neocaptainnemo.cv.ui.contacts.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.compose.*
import com.neocaptainnemo.cv.ui.contacts.ContactSection
import com.neocaptainnemo.cv.ui.contacts.ContactType

typealias ContactItemClicked = ((contact: ContactSection) -> Unit)

const val CONTACTS_ITEM_SEMANTICS_ROW = "ContactItem_row_"

@Composable
fun ContactItem(
        contact: ContactSection,
        itemClicked: ContactItemClicked?,
) {
    Row(modifier = Modifier.fillMaxWidth()
            .semantics { testTag = "$CONTACTS_ITEM_SEMANTICS_ROW${contact.value}" }
            .clickable(onClick = {
                itemClicked?.invoke(contact)
            })
            .padding(horizontal = DEFAULT_MARGIN,
                     vertical = SMALL_MARGIN)) {
        Image(asset = vectorResource(id = contact.img),
              colorFilter = ColorFilter(Color.Primary, BlendMode.SrcIn),
              modifier = Modifier.align(Alignment.CenterVertically))
        Column(modifier = Modifier.padding(start = DEFAULT_MARGIN)) {

            Text(text = stringResource(id = contact.title),
                 style = TextStyle.Primary16
            )
            Text(text = stringResource(id = contact.subTitle),
                 style = TextStyle.Secondary14)
        }
    }
}

@Preview(widthDp = 200)
@Composable
fun PreviewContactItem() {
    MaterialTheme(colors = CvColors) {
        ContactItem(contact = ContactSection(
                ContactType.CV,
                R.string.title_cv,
                R.string.action_cv,
                R.drawable.ic_contacts_black_24px,
                "value"
        ),
                    itemClicked = null)
    }
}
