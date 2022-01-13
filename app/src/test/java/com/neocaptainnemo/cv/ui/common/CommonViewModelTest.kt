package com.neocaptainnemo.cv.ui.common

import app.cash.turbine.test
import com.neocaptainnemo.cv.core.data.CvRepository
import com.neocaptainnemo.cv.core.model.CommonSection
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
class CommonViewModelTest : ShouldSpec({

    val cvRepository: CvRepository = mockk()

    lateinit var viewModel: CommonViewModel

    beforeTest {
        Dispatchers.setMain(StandardTestDispatcher())

        viewModel = CommonViewModel(cvRepository)
    }

    afterTest {
        clearAllMocks(true)
    }

    should("progress during successful fetch") {

        every {
            cvRepository.commons()
        } returns flowOf(listOf())

        launch {
            viewModel.progress.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.commons().collect()
        }
    }

    should("progress during failed fetch") {

        every {
            cvRepository.commons()
        } throws RuntimeException()

        launch {
            viewModel.progress.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.commons().collect()
        }
    }

    should("empty state") {
        every {
            cvRepository.commons()
        } returns flowOf(listOf())

        launch {
            viewModel.empty.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.commons().collect()
        }
    }

    should("not empty state") {
        every {
            cvRepository.commons()
        } returns flowOf(
            listOf(
                CommonSection(),
                CommonSection()
            )
        )

        launch {
            viewModel.empty.test {
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.commons().collect()
        }
    }

    should("successful fetch") {

        val commonSection1 = CommonSection()
        val commonSection2 = CommonSection()

        every {
            cvRepository.commons()
        } returns flowOf(
            listOf(
                commonSection1,
                commonSection2
            )
        )

        launch {

            viewModel.commons().test {
                awaitItem() shouldBe listOf(commonSection1, commonSection2)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("failed fetch") {

        every {
            cvRepository.commons()
        } returns flow { throw RuntimeException() }

        launch {

            viewModel.commons().test {
                awaitItem() shouldBe listOf()
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
})
