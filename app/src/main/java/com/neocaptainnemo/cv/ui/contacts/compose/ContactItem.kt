package com.neocaptainnemo.cv.ui.contacts.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.compose.cvColors
import com.neocaptainnemo.cv.ui.compose.defaultMargin
import com.neocaptainnemo.cv.ui.compose.primary16
import com.neocaptainnemo.cv.ui.compose.secondary14
import com.neocaptainnemo.cv.ui.compose.smallMargin
import com.neocaptainnemo.cv.ui.contacts.ContactSection
import com.neocaptainnemo.cv.ui.contacts.ContactType

typealias ContactItemClicked = ((contact: ContactSection) -> Unit)

const val CONTACTS_ITEM_SEMANTICS_ROW = "ContactItem_row_"

@Composable
fun ContactItem(
    contact: ContactSection,
    itemClicked: ContactItemClicked?,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .semantics { testTag = "$CONTACTS_ITEM_SEMANTICS_ROW${contact.value}" }
            .clickable(onClick = {
                itemClicked?.invoke(contact)
            })
            .padding(
                horizontal = defaultMargin,
                vertical = smallMargin
            )
    ) {

        Icon(
            painter = painterResource(id = contact.img),
            contentDescription = null,
            // colorFilter = ColorFilter(Color.Primary, BlendMode.SrcIn),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Column(modifier = Modifier.padding(start = defaultMargin)) {

            Text(
                text = stringResource(id = contact.title),
                style = TextStyle.primary16()
            )
            Text(
                text = stringResource(id = contact.subTitle),
                style = TextStyle.secondary14()
            )
        }
    }
}

@Preview(widthDp = 200)
@Composable
fun PreviewContactItem() {
    MaterialTheme(colors = cvColors()) {
        ContactItem(
            contact = ContactSection(
                ContactType.CV,
                R.string.title_cv,
                R.string.action_cv,
                R.drawable.ic_email,
                "value"
            ),
            itemClicked = null
        )
    }
}
