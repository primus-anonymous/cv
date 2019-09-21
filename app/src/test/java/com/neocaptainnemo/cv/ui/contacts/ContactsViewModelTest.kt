package com.neocaptainnemo.cv.ui.contacts

import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.RxTestRule
import com.neocaptainnemo.cv.model.Contacts
import com.neocaptainnemo.cv.services.IDataService
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ContactsViewModelTest {

    @JvmField
    @Rule
    val rxTestRule = RxTestRule()

    @Mock
    lateinit var dataService: IDataService

    lateinit var viewModel: ContactsViewModel

    @Before
    fun setUp() {
        viewModel = ContactsViewModel(dataService)
    }

    @Test
    fun `progress during successful fetch`() {

        whenever(dataService.contacts()).then { Observable.just(Contacts()) }

        val testable = viewModel.progress.test()

        viewModel.contacts().test().assertNoErrors()

        testable.assertValues(false, true, false)
    }

    @Test
    fun `progress during failed fetch`() {

        whenever(dataService.contacts()).then { Observable.error<Contacts>(RuntimeException()) }

        val testable = viewModel.progress.test()

        viewModel.contacts().test().assertNoErrors()

        testable.assertValues(false, true, false)
    }


    @Test
    fun `successful fetch`() {

        val response = Contacts()

        val profession = "professsion"
        response.profession = profession

        val name = "name"
        response.name = name

        val pic = "http://userpicimage"
        response.userPic = pic


        val email = "email@email.com"
        val phone = "89032345678"
        val cvUrl = "http://www.fdfs.fsdfsd/pdf"
        val github = "http://github/fdfsd"

        response.email = email
        response.phone = phone
        response.cv = cvUrl
        response.gitHub = github

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.contacts().test().assertValue(listOf(
                ContactsHeader(pic, name, profession),
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phone),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, email),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, github),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrl)
        ))
    }

    @Test
    fun `contacts list with no email`() {

        val response = Contacts()

        val profession = "professsion"
        response.profession = profession

        val name = "name"
        response.name = name

        val pic = "http://userpicimage"
        response.userPic = pic


        val phone = "89032345678"
        val cvUrl = "http://www.fdfs.fsdfsd/pdf"
        val github = "http://github/fdfsd"

        response.phone = phone
        response.cv = cvUrl
        response.gitHub = github

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.contacts().test().assertValue(listOf(
                ContactsHeader(pic, name, profession),
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phone),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, github),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrl)
        ))

    }


    @Test
    fun `contacts list with no phone`() {

        val response = Contacts()

        val profession = "professsion"
        response.profession = profession

        val name = "name"
        response.name = name

        val pic = "http://userpicimage"
        response.userPic = pic

        val email = "email@email.com"
        val cvUrl = "http://www.fdfs.fsdfsd/pdf"
        val github = "http://github/fdfsd"

        response.email = email
        response.cv = cvUrl
        response.gitHub = github

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.contacts().test().assertValue(listOf(
                ContactsHeader(pic, name, profession),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, email),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, github),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrl)
        ))
    }


    @Test
    fun `contacts list with no cv`() {

        val response = Contacts()

        val profession = "professsion"
        response.profession = profession

        val name = "name"
        response.name = name

        val pic = "http://userpicimage"
        response.userPic = pic

        val email = "email@email.com"
        val phone = "89032345678"
        val github = "http://github/fdfsd"

        response.email = email
        response.phone = phone
        response.gitHub = github

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.contacts().test().assertValue(listOf(
                ContactsHeader(pic, name, profession),
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phone),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, email),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, github)
        ))
    }

    @Test
    fun `contacts list with no github`() {

        val response = Contacts()

        val profession = "professsion"
        response.profession = profession

        val name = "name"
        response.name = name

        val pic = "http://userpicimage"
        response.userPic = pic

        val email = "email@email.com"
        val phone = "89032345678"
        val cvUrl = "http://www.fdfs.fsdfsd/pdf"

        response.email = email
        response.phone = phone
        response.cv = cvUrl

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.contacts().test().assertValue(listOf(
                ContactsHeader(pic, name, profession),
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phone),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, email),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrl)
        ))
    }

    @Test
    fun `failed fetch`() {

        whenever(dataService.contacts()).then { Observable.error<List<ContactSection>>(RuntimeException()) }

        viewModel.contacts().test().assertValue(listOf())
    }
}