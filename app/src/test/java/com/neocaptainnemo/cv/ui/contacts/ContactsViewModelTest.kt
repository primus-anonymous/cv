package com.neocaptainnemo.cv.ui.contacts

import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.data.DataService
import com.neocaptainnemo.cv.core.model.Contacts
import com.neocaptainnemo.cv.ui.TestCoroutineRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ContactsViewModelTest {


    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Mock
    lateinit var dataService: DataService

    lateinit var viewModel: ContactsViewModel

    @Before
    fun setUp() {
        viewModel = ContactsViewModel(dataService)
    }

    @Test
    fun `progress during successful fetch`() = runBlockingTest {

        whenever(dataService.contacts()).doAnswer { flowOf(Contacts()) }

        val progressValues = mutableListOf<Boolean>()

        val progressJob = launch {
            viewModel.progress.collect {
                progressValues.add(it)
            }
        }

        val contactsJob = launch {
            viewModel.contacts()
                    .collect { }
        }

        assertThat(progressValues)
                .isEqualTo(
                     listOf(false,
                            true,
                            false))

        progressJob.cancel()
        contactsJob.cancel()
    }

    @Test
    fun `progress during failed fetch`() = runBlockingTest {

        whenever(dataService.contacts()).doAnswer { flow { throw RuntimeException() } }

        val progressValues = mutableListOf<Boolean>()

        val progressJob = launch {
            viewModel.progress.collect {
                progressValues.add(it)
            }
        }

        val contactsJob = launch {
            viewModel.contacts()
                    .collect { }
        }

        assertThat(progressValues)
                .isEqualTo(
                     listOf(false,
                            true,
                            false))

        progressJob.cancel()
        contactsJob.cancel()
    }


    @Test
    fun `successful fetch`() = runBlockingTest {

        val professionValue = "professsion"

        val nameValue = "name"

        val picValue = "http://userpicimage"

        val emailValue = "email@email.com"
        val phoneValue = "89032345678"
        val cvUrlValue = "http://www.fdfs.fsdfsd/pdf"
        val githubValue = "http://github/fdfsd"

        val response = Contacts().apply {
            profession = professionValue
            name = nameValue
            userPic = picValue
            email = emailValue
            phone = phoneValue
            cvUrl = cvUrlValue
            github = githubValue
        }

        whenever(dataService.contacts()).doAnswer { flowOf(response) }

        val contactValues = mutableListOf<List<Any>>()

        val contactsJob = launch {
            viewModel.contacts()
                    .collect {
                        contactValues.add(it)
                    }
        }

        val expected = listOf(
                ContactsHeader(picValue,
                               nameValue,
                               professionValue),
                ContactSection(ContactType.PHONE,
                               R.string.action_phone,
                               R.string.tap_to_call,
                               R.drawable.ic_call_black_24px,
                               phoneValue),
                ContactSection(ContactType.EMAIL,
                               R.string.action_email,
                               R.string.tap_to_send_email,
                               R.drawable.ic_email_black_24px,
                               emailValue),
                ContactSection(ContactType.GIT_HUB,
                               R.string.action_github,
                               R.string.tap_to_view_github,
                               R.drawable.ic_link_black_24px,
                               githubValue),
                ContactSection(ContactType.CV,
                               R.string.action_cv,
                               R.string.tap_to_save_cv,
                               R.drawable.ic_save_white_24px,
                               cvUrlValue)
        )

        assertThat(contactValues)
                .isEqualTo(
                     listOf(expected))

        contactsJob.cancel()
    }

    @Test
    fun `contacts list with no email`() = runBlockingTest {

        val professionValue = "professsion"

        val nameValue = "name"

        val picValue = "http://userpicimage"

        val phoneValue = "89032345678"
        val cvUrlValue = "http://www.fdfs.fsdfsd/pdf"
        val githubValue = "http://github/fdfsd"

        val response = Contacts().apply {
            profession = professionValue
            name = nameValue
            userPic = picValue
            phone = phoneValue
            cvUrl = cvUrlValue
            github = githubValue
        }

        whenever(dataService.contacts()).doAnswer { flowOf(response) }

        val contactValues = mutableListOf<List<Any>>()

        val contactsJob = launch {
            viewModel.contacts()
                    .collect {
                        contactValues.add(it)
                    }
        }

        val expected = listOf(
                ContactsHeader(picValue,
                               nameValue,
                               professionValue),
                ContactSection(ContactType.PHONE,
                               R.string.action_phone,
                               R.string.tap_to_call,
                               R.drawable.ic_call_black_24px,
                               phoneValue),
                ContactSection(ContactType.GIT_HUB,
                               R.string.action_github,
                               R.string.tap_to_view_github,
                               R.drawable.ic_link_black_24px,
                               githubValue),
                ContactSection(ContactType.CV,
                               R.string.action_cv,
                               R.string.tap_to_save_cv,
                               R.drawable.ic_save_white_24px,
                               cvUrlValue)
        )

        assertThat(contactValues)
                .isEqualTo(
                     listOf(expected))

        contactsJob.cancel()
    }


    @Test
    fun `contacts list with no phone`() = runBlockingTest {

        val professionValue = "professsion"

        val nameValue = "name"

        val picValue = "http://userpicimage"

        val emailValue = "email@email.com"
        val cvUrlValue = "http://www.fdfs.fsdfsd/pdf"
        val githubValue = "http://github/fdfsd"

        val response = Contacts().apply {
            profession = professionValue
            name = nameValue
            userPic = picValue
            email = emailValue
            cvUrl = cvUrlValue
            github = githubValue
        }

        whenever(dataService.contacts()).doAnswer { flowOf(response) }

        val contactValues = mutableListOf<List<Any>>()

        val contactsJob = launch {
            viewModel.contacts()
                    .collect {
                        contactValues.add(it)
                    }
        }

        val expected = listOf(
                ContactsHeader(picValue,
                               nameValue,
                               professionValue),
                ContactSection(ContactType.EMAIL,
                               R.string.action_email,
                               R.string.tap_to_send_email,
                               R.drawable.ic_email_black_24px,
                               emailValue),
                ContactSection(ContactType.GIT_HUB,
                               R.string.action_github,
                               R.string.tap_to_view_github,
                               R.drawable.ic_link_black_24px,
                               githubValue),
                ContactSection(ContactType.CV,
                               R.string.action_cv,
                               R.string.tap_to_save_cv,
                               R.drawable.ic_save_white_24px,
                               cvUrlValue)
        )

        assertThat(contactValues)
                .isEqualTo(
                     listOf(expected))

        contactsJob.cancel()
    }


    @Test
    fun `contacts list with no cv`() = runBlockingTest {
        val professionValue = "professsion"

        val nameValue = "name"

        val picValue = "http://userpicimage"

        val emailValue = "email@email.com"
        val phoneValue = "89032345678"
        val githubValue = "http://github/fdfsd"

        val response = Contacts().apply {
            profession = professionValue
            name = nameValue
            userPic = picValue
            email = emailValue
            phone = phoneValue
            github = githubValue
        }

        whenever(dataService.contacts()).doAnswer { flowOf(response) }

        val contactValues = mutableListOf<List<Any>>()

        val contactsJob = launch {
            viewModel.contacts()
                    .collect {
                        contactValues.add(it)
                    }
        }

        val expected = listOf(
                ContactsHeader(picValue,
                               nameValue,
                               professionValue),
                ContactSection(ContactType.PHONE,
                               R.string.action_phone,
                               R.string.tap_to_call,
                               R.drawable.ic_call_black_24px,
                               phoneValue),
                ContactSection(ContactType.EMAIL,
                               R.string.action_email,
                               R.string.tap_to_send_email,
                               R.drawable.ic_email_black_24px,
                               emailValue),
                ContactSection(ContactType.GIT_HUB,
                               R.string.action_github,
                               R.string.tap_to_view_github,
                               R.drawable.ic_link_black_24px,
                               githubValue)
        )

        assertThat(contactValues)
                .isEqualTo(
                     listOf(expected))

        contactsJob.cancel()

    }

    @Test
    fun `contacts list with no github`() = runBlockingTest {
        val professionValue = "professsion"

        val nameValue = "name"

        val picValue = "http://userpicimage"

        val emailValue = "email@email.com"
        val phoneValue = "89032345678"
        val cvUrlValue = "http://www.fdfs.fsdfsd/pdf"

        val response = Contacts().apply {
            profession = professionValue
            name = nameValue
            userPic = picValue
            email = emailValue
            phone = phoneValue
            cvUrl = cvUrlValue
        }

        whenever(dataService.contacts()).doAnswer { flowOf(response) }

        val contactValues = mutableListOf<List<Any>>()

        val contactsJob = launch {
            viewModel.contacts()
                    .collect {
                        contactValues.add(it)
                    }
        }

        val expected = listOf(
                ContactsHeader(picValue,
                               nameValue,
                               professionValue),
                ContactSection(ContactType.PHONE,
                               R.string.action_phone,
                               R.string.tap_to_call,
                               R.drawable.ic_call_black_24px,
                               phoneValue),
                ContactSection(ContactType.EMAIL,
                               R.string.action_email,
                               R.string.tap_to_send_email,
                               R.drawable.ic_email_black_24px,
                               emailValue),
                ContactSection(ContactType.CV,
                               R.string.action_cv,
                               R.string.tap_to_save_cv,
                               R.drawable.ic_save_white_24px,
                               cvUrlValue)
        )

        assertThat(contactValues)
                .isEqualTo(
                     listOf(expected))

        contactsJob.cancel()

    }

    @Test
    fun `failed fetch`() = runBlockingTest {

        whenever(dataService.contacts()).doAnswer { flow { throw RuntimeException() } }

        val contactValues = mutableListOf<List<Any>>()

        val contactsJob = launch {
            viewModel.contacts()
                    .collect {
                        contactValues.add(it)
                    }
        }

        assertThat(contactValues)
                .isEqualTo(
                     listOf(listOf<Any>()))

        contactsJob.cancel()
    }
}