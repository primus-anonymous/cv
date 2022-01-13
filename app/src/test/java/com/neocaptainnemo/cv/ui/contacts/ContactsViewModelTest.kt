package com.neocaptainnemo.cv.ui.contacts

import app.cash.turbine.test
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.data.DataService
import com.neocaptainnemo.cv.core.model.Contacts
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class ContactsViewModelTest : ShouldSpec({

    val dataService: DataService = mockk()

    lateinit var viewModel: ContactsViewModel

    beforeTest {
        Dispatchers.setMain(StandardTestDispatcher())

        viewModel = ContactsViewModel(dataService)
    }

    afterTest {
        clearAllMocks(true)
    }

    should("progress during successful fetch") {

        every {
            dataService.contacts()
        } returns flowOf(Contacts())

        launch {
            viewModel.progress.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.contacts().collect()
        }
    }

    should("progress during failed fetch") {

        every {
            dataService.contacts()
        } throws RuntimeException()

        launch {
            viewModel.progress.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.contacts().collect()
        }
    }

    should("successful fetch") {

        val professionValue = "professsion"

        val nameValue = "name"

        val picValue = "http://userpicimage"

        val emailValue = "email@email.com"
        val phoneValue = "89032345678"
        val cvUrlValue = "http://www.fdfs.fsdfsd/pdf"
        val githubValue = "http://github/fdfsd"
        val telegramValue = "telegram"

        val response = Contacts().apply {
            profession = professionValue
            name = nameValue
            userPic = picValue
            email = emailValue
            phone = phoneValue
            cvUrl = cvUrlValue
            github = githubValue
            telegram = telegramValue
        }

        every {
            dataService.contacts()
        } returns flowOf(response)

        val expected = listOf(
            ContactsHeader(
                picValue,
                nameValue,
                professionValue
            ),
            ContactSection(
                ContactType.PHONE,
                R.string.action_phone,
                R.string.tap_to_call,
                R.drawable.ic_call,
                phoneValue
            ),
            ContactSection(
                ContactType.TELEGRAM,
                R.string.action_telegram,
                R.string.tap_to_open_telegram,
                R.drawable.ic_telegram,
                telegramValue
            ),
            ContactSection(
                ContactType.EMAIL,
                R.string.action_email,
                R.string.tap_to_send_email,
                R.drawable.ic_email,
                emailValue
            ),
            ContactSection(
                ContactType.GIT_HUB,
                R.string.action_github,
                R.string.tap_to_view_github,
                R.drawable.ic_github,
                githubValue
            ),
            ContactSection(
                ContactType.CV,
                R.string.action_cv,
                R.string.tap_to_save_cv,
                R.drawable.ic_cv,
                cvUrlValue
            )
        )

        launch {
            viewModel.contacts().test {
                awaitItem() shouldBe expected

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("contacts list with no email") {

        val professionValue = "professsion"

        val nameValue = "name"

        val picValue = "http://userpicimage"

        val phoneValue = "89032345678"
        val cvUrlValue = "http://www.fdfs.fsdfsd/pdf"
        val githubValue = "http://github/fdfsd"
        val telegramValue = "telegram"

        val response = Contacts().apply {
            profession = professionValue
            name = nameValue
            userPic = picValue
            phone = phoneValue
            cvUrl = cvUrlValue
            github = githubValue
            telegram = telegramValue
        }

        every {
            dataService.contacts()
        } returns flowOf(response)

        val expected = listOf(
            ContactsHeader(
                picValue,
                nameValue,
                professionValue
            ),
            ContactSection(
                ContactType.PHONE,
                R.string.action_phone,
                R.string.tap_to_call,
                R.drawable.ic_call,
                phoneValue
            ),
            ContactSection(
                ContactType.TELEGRAM,
                R.string.action_telegram,
                R.string.tap_to_open_telegram,
                R.drawable.ic_telegram,
                telegramValue
            ),
            ContactSection(
                ContactType.GIT_HUB,
                R.string.action_github,
                R.string.tap_to_view_github,
                R.drawable.ic_github,
                githubValue
            ),
            ContactSection(
                ContactType.CV,
                R.string.action_cv,
                R.string.tap_to_save_cv,
                R.drawable.ic_cv,
                cvUrlValue
            )
        )

        launch {
            viewModel.contacts().test {
                awaitItem() shouldBe expected

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("contacts list with no phone") {

        val professionValue = "professsion"

        val nameValue = "name"

        val picValue = "http://userpicimage"

        val emailValue = "email@email.com"
        val cvUrlValue = "http://www.fdfs.fsdfsd/pdf"
        val githubValue = "http://github/fdfsd"
        val telegramValue = "telegram"

        val response = Contacts().apply {
            profession = professionValue
            name = nameValue
            userPic = picValue
            email = emailValue
            cvUrl = cvUrlValue
            github = githubValue
            telegram = telegramValue
        }

        every {
            dataService.contacts()
        } returns flowOf(response)

        val expected = listOf(
            ContactsHeader(
                picValue,
                nameValue,
                professionValue
            ),
            ContactSection(
                ContactType.TELEGRAM,
                R.string.action_telegram,
                R.string.tap_to_open_telegram,
                R.drawable.ic_telegram,
                telegramValue
            ),
            ContactSection(
                ContactType.EMAIL,
                R.string.action_email,
                R.string.tap_to_send_email,
                R.drawable.ic_email,
                emailValue
            ),
            ContactSection(
                ContactType.GIT_HUB,
                R.string.action_github,
                R.string.tap_to_view_github,
                R.drawable.ic_github,
                githubValue
            ),
            ContactSection(
                ContactType.CV,
                R.string.action_cv,
                R.string.tap_to_save_cv,
                R.drawable.ic_cv,
                cvUrlValue
            )
        )

        launch {
            viewModel.contacts().test {
                awaitItem() shouldBe expected

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("contacts list with no cv") {
        val professionValue = "professsion"

        val nameValue = "name"

        val picValue = "http://userpicimage"

        val emailValue = "email@email.com"
        val phoneValue = "89032345678"
        val githubValue = "http://github/fdfsd"
        val telegramValue = "telegram"

        val response = Contacts().apply {
            profession = professionValue
            name = nameValue
            userPic = picValue
            email = emailValue
            phone = phoneValue
            github = githubValue
            telegram = telegramValue
        }

        every {
            dataService.contacts()
        } returns flowOf(response)

        val expected = listOf(
            ContactsHeader(
                picValue,
                nameValue,
                professionValue
            ),
            ContactSection(
                ContactType.PHONE,
                R.string.action_phone,
                R.string.tap_to_call,
                R.drawable.ic_call,
                phoneValue
            ),
            ContactSection(
                ContactType.TELEGRAM,
                R.string.action_telegram,
                R.string.tap_to_open_telegram,
                R.drawable.ic_telegram,
                telegramValue
            ),
            ContactSection(
                ContactType.EMAIL,
                R.string.action_email,
                R.string.tap_to_send_email,
                R.drawable.ic_email,
                emailValue
            ),
            ContactSection(
                ContactType.GIT_HUB,
                R.string.action_github,
                R.string.tap_to_view_github,
                R.drawable.ic_github,
                githubValue
            )
        )

        launch {
            viewModel.contacts().test {
                awaitItem() shouldBe expected

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("contacts list with no github") {
        val professionValue = "professsion"

        val nameValue = "name"

        val picValue = "http://userpicimage"

        val emailValue = "email@email.com"
        val phoneValue = "89032345678"
        val cvUrlValue = "http://www.fdfs.fsdfsd/pdf"
        val telegramValue = "telegram"

        val response = Contacts().apply {
            profession = professionValue
            name = nameValue
            userPic = picValue
            email = emailValue
            phone = phoneValue
            cvUrl = cvUrlValue
            telegram = telegramValue
        }

        every {
            dataService.contacts()
        } returns flowOf(response)

        val expected = listOf(
            ContactsHeader(
                picValue,
                nameValue,
                professionValue
            ),
            ContactSection(
                ContactType.PHONE,
                R.string.action_phone,
                R.string.tap_to_call,
                R.drawable.ic_call,
                phoneValue
            ),
            ContactSection(
                ContactType.TELEGRAM,
                R.string.action_telegram,
                R.string.tap_to_open_telegram,
                R.drawable.ic_telegram,
                telegramValue
            ),
            ContactSection(
                ContactType.EMAIL,
                R.string.action_email,
                R.string.tap_to_send_email,
                R.drawable.ic_email,
                emailValue
            ),
            ContactSection(
                ContactType.CV,
                R.string.action_cv,
                R.string.tap_to_save_cv,
                R.drawable.ic_cv,
                cvUrlValue
            )
        )

        launch {
            viewModel.contacts().test {
                awaitItem() shouldBe expected

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("contacts list with no telegram") {

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

        every {
            dataService.contacts()
        } returns flowOf(response)

        val expected = listOf(
            ContactsHeader(
                picValue,
                nameValue,
                professionValue
            ),
            ContactSection(
                ContactType.PHONE,
                R.string.action_phone,
                R.string.tap_to_call,
                R.drawable.ic_call,
                phoneValue
            ),
            ContactSection(
                ContactType.EMAIL,
                R.string.action_email,
                R.string.tap_to_send_email,
                R.drawable.ic_email,
                emailValue
            ),
            ContactSection(
                ContactType.GIT_HUB,
                R.string.action_github,
                R.string.tap_to_view_github,
                R.drawable.ic_github,
                githubValue
            ),
            ContactSection(
                ContactType.CV,
                R.string.action_cv,
                R.string.tap_to_save_cv,
                R.drawable.ic_cv,
                cvUrlValue
            )
        )

        launch {
            viewModel.contacts().test {
                awaitItem() shouldBe expected

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("failed fetch") {

        every {
            dataService.contacts()
        } returns flow { throw RuntimeException() }

        launch {
            viewModel.contacts().test {
                awaitItem() shouldBe listOf()

                cancelAndIgnoreRemainingEvents()
            }
        }
    }
})
