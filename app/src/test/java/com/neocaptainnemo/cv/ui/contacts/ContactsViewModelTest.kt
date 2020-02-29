package com.neocaptainnemo.cv.ui.contacts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.Contacts
import com.neocaptainnemo.cv.services.IDataService
import com.neocaptainnemo.cv.ui.TestCoroutineRule
import com.neocaptainnemo.cv.ui.adapter.AdapterItem
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Mock
    lateinit var dataService: IDataService

    lateinit var viewModel: ContactsViewModel

    @Before
    fun setUp() {
        viewModel = ContactsViewModel(dataService)
    }

    @Test
    fun `progress during successful fetch`() {

        whenever(dataService.contacts()).doAnswer { flowOf(Contacts()) }

        val progressObserver = mock<Observer<Boolean>>()
        val order = inOrder(progressObserver, progressObserver, progressObserver)

        viewModel.progress.observeForever(progressObserver)

        viewModel.contacts().observeForever { }

        order.verify(progressObserver).onChanged(false)
        order.verify(progressObserver).onChanged(true)
        order.verify(progressObserver).onChanged(false)
    }

    @Test
    fun `progress during failed fetch`() {

        whenever(dataService.contacts()).doAnswer { flow { throw RuntimeException() } }

        val progressObserver = mock<Observer<Boolean>>()
        val order = inOrder(progressObserver, progressObserver, progressObserver)

        viewModel.progress.observeForever(progressObserver)

        viewModel.contacts().observeForever { }

        order.verify(progressObserver).onChanged(false)
        order.verify(progressObserver).onChanged(true)
        order.verify(progressObserver).onChanged(false)
    }


    @Test
    fun `successful fetch`() {

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

        val contactsObserver = mock<Observer<List<AdapterItem>>>()

        viewModel.contacts().observeForever(contactsObserver)

        val expected = listOf(
                ContactsHeader(picValue, nameValue, professionValue),
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phoneValue),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, emailValue),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, githubValue),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrlValue)
        )

        verify(contactsObserver).onChanged(expected)
    }

    @Test
    fun `contacts list with no email`() {

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

        val contactsObserver = mock<Observer<List<AdapterItem>>>()

        viewModel.contacts().observeForever(contactsObserver)

        val expected = listOf(
                ContactsHeader(picValue, nameValue, professionValue),
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phoneValue),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, githubValue),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrlValue)
        )

        verify(contactsObserver).onChanged(expected)

    }


    @Test
    fun `contacts list with no phone`() {

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

        val contactsObserver = mock<Observer<List<AdapterItem>>>()

        viewModel.contacts().observeForever(contactsObserver)

        val expected = listOf(
                ContactsHeader(picValue, nameValue, professionValue),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, emailValue),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, githubValue),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrlValue)
        )

        verify(contactsObserver).onChanged(expected)
    }


    @Test
    fun `contacts list with no cv`() {
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

        val contactsObserver = mock<Observer<List<AdapterItem>>>()

        viewModel.contacts().observeForever(contactsObserver)

        val expected = listOf(
                ContactsHeader(picValue, nameValue, professionValue),
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phoneValue),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, emailValue),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, githubValue)
        )

        verify(contactsObserver).onChanged(expected)

    }

    @Test
    fun `contacts list with no github`() {
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

        val contactsObserver = mock<Observer<List<AdapterItem>>>()

        viewModel.contacts().observeForever(contactsObserver)

        val expected = listOf(
                ContactsHeader(picValue, nameValue, professionValue),
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phoneValue),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, emailValue),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrlValue)
        )

        verify(contactsObserver).onChanged(expected)

    }

    @Test
    fun `failed fetch`() {

        whenever(dataService.contacts()).doAnswer { flow { throw RuntimeException() } }

        val contactsObserver = mock<Observer<List<AdapterItem>>>()

        viewModel.contacts().observeForever(contactsObserver)

        verify(contactsObserver).onChanged(listOf())
    }
}