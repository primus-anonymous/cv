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
    fun progressSuccess() {

        whenever(dataService.contacts()).then { Observable.just(Contacts()) }

        val testable = viewModel.progress().test()

        viewModel.profilePic().test().assertNoErrors()

        testable.assertValues(false, true, false)
    }

    @Test
    fun progressFailure() {

        whenever(dataService.contacts()).then { Observable.error<Contacts>(RuntimeException()) }

        val testable = viewModel.progress().test()

        viewModel.profilePic().test().assertNoErrors()

        testable.assertValues(false, true, false)
    }

    @Test
    fun userPicSuccess() {

        val response = Contacts()

        val pic = "http://userpicimage"

        response.userPic = pic

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.profilePic().test().assertValue(pic)
    }

    @Test
    fun userPicError() {

        whenever(dataService.contacts()).then { Observable.error<Contacts>(RuntimeException()) }

        viewModel.profilePic().test().assertValue("")
    }

    @Test
    fun nameSuccess() {

        val response = Contacts()

        val name = "name"

        response.name = name

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.name().test().assertValue(name)
    }

    @Test
    fun nameError() {

        whenever(dataService.contacts()).then { Observable.error<Contacts>(RuntimeException()) }

        viewModel.name().test().assertValue("")
    }

    @Test
    fun professionSuccess() {

        val response = Contacts()

        val profession = "professsion"

        response.profession = profession

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.profession().test().assertValue(profession)
    }

    @Test
    fun professionError() {

        whenever(dataService.contacts()).then { Observable.error<Contacts>(RuntimeException()) }

        viewModel.profession().test().assertValue("")
    }


    @Test
    fun contactsSuccess() {

        val response = Contacts()

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
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phone),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, email),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, github),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrl)
        ))
    }

    @Test
    fun contactsNoEmail() {

        val response = Contacts()

        val phone = "89032345678"
        val cvUrl = "http://www.fdfs.fsdfsd/pdf"
        val github = "http://github/fdfsd"

        response.phone = phone
        response.cv = cvUrl
        response.gitHub = github

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.contacts().test().assertValue(listOf(
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phone),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, github),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrl)
        ))

    }


    @Test
    fun contactsNoPhone() {

        val response = Contacts()

        val email = "email@email.com"
        val cvUrl = "http://www.fdfs.fsdfsd/pdf"
        val github = "http://github/fdfsd"

        response.email = email
        response.cv = cvUrl
        response.gitHub = github

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.contacts().test().assertValue(listOf(
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, email),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, github),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrl)
        ))
    }


    @Test
    fun contactsNoCv() {

        val response = Contacts()

        val email = "email@email.com"
        val phone = "89032345678"
        val github = "http://github/fdfsd"

        response.email = email
        response.phone = phone
        response.gitHub = github

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.contacts().test().assertValue(listOf(
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phone),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, email),
                ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, github)
        ))
    }

    @Test
    fun contactsNoGithub() {

        val response = Contacts()

        val email = "email@email.com"
        val phone = "89032345678"
        val cvUrl = "http://www.fdfs.fsdfsd/pdf"

        response.email = email
        response.phone = phone
        response.cv = cvUrl

        whenever(dataService.contacts()).then { Observable.just(response) }

        viewModel.contacts().test().assertValue(listOf(
                ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, phone),
                ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, email),
                ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, cvUrl)
        ))
    }

    @Test
    fun contactsError() {

        whenever(dataService.contacts()).then { Observable.error<List<ContactSection>>(RuntimeException()) }

        viewModel.contacts().test().assertValue(listOf())
    }
}