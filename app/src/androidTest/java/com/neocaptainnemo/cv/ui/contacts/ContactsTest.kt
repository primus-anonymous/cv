package com.neocaptainnemo.cv.ui.contacts

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.neocaptainnemo.cv.MainActivity
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.compose.CvColors
import com.neocaptainnemo.cv.ui.contacts.compose.CONTACTS_HEADER_SEMANTICS_NAME
import com.neocaptainnemo.cv.ui.contacts.compose.CONTACTS_HEADER_SEMANTICS_PROF
import com.neocaptainnemo.cv.ui.contacts.compose.CONTACTS_ITEM_SEMANTICS_ROW
import com.neocaptainnemo.cv.ui.contacts.compose.ContactsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
class ContactsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun nameAndProfessionVisible() {
        val contactsFlow: Flow<List<Any>> = flowOf(
                listOf(ContactsHeader("http://image.jpg", "Name", "Profession"))
        )
        val progressFlow = flowOf(false)

        composeTestRule.setContent {
            MaterialTheme(colors = CvColors) {
                ContactsScreen(contactsFlow = contactsFlow, progressFlow = progressFlow,
                               itemClicked = {

                               })
            }
        }

        composeTestRule.onNodeWithTag(CONTACTS_HEADER_SEMANTICS_NAME)
                .assertTextEquals("Name")
        composeTestRule.onNodeWithTag(CONTACTS_HEADER_SEMANTICS_PROF)
                .assertTextEquals("Profession")
    }

    @Test
    fun phoneClickable() {
        val itemValue = "value"

        val contactsFlow: Flow<List<Any>> = flowOf(
                listOf(
                        ContactSection(ContactType.PHONE, R.string.title_contacts,
                                       R.string.tap_to_call,
                                       R.drawable.ic_call_black_24px,
                                       itemValue))
        )
        val progressFlow = flowOf(false)

        composeTestRule.setContent {
            MaterialTheme(colors = CvColors) {
                ContactsScreen(contactsFlow = contactsFlow, progressFlow = progressFlow,
                               itemClicked = {

                               })
            }
        }

        composeTestRule.onNodeWithTag("$CONTACTS_ITEM_SEMANTICS_ROW$itemValue")
                .performClick()
    }
}