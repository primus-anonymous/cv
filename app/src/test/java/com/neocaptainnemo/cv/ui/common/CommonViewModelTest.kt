package com.neocaptainnemo.cv.ui.common

import app.cash.turbine.test
import app.cash.turbine.testIn
import app.cash.turbine.turbineScope
import com.neocaptainnemo.cv.core.data.CvRepository
import com.neocaptainnemo.cv.core.model.CommonSection
import io.kotest.core.coroutines.backgroundScope
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain

class CommonViewModelTest : ShouldSpec({

    val cvRepository: CvRepository = mockk()

    lateinit var viewModel: CommonViewModel

    beforeTest {
        viewModel = CommonViewModel(cvRepository)
    }

    afterTest {
        clearAllMocks(true)
    }

    should("progress during successful fetch") {

        every {
            cvRepository.commons()
        } returns flowOf(listOf())


        viewModel.progress.test {

            awaitItem() shouldBe false

            viewModel.commons().collect()

            awaitItem() shouldBe true
            awaitItem() shouldBe false

            cancelAndIgnoreRemainingEvents()
        }

    }

    should("progress during failed fetch") {

        every {
            cvRepository.commons()
        } returns flow {
            throw RuntimeException()
        }

        viewModel.progress.test {
            awaitItem() shouldBe false

            viewModel.commons().collect()

            awaitItem() shouldBe true
            awaitItem() shouldBe false

            cancelAndIgnoreRemainingEvents()
        }

    }

    should("empty state") {
        every {
            cvRepository.commons()
        } returns flowOf(listOf())

        viewModel.empty.test {
            awaitItem() shouldBe false

            viewModel.commons().collect()

            awaitItem() shouldBe true

            cancelAndIgnoreRemainingEvents()
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

        viewModel.empty.test {
            awaitItem() shouldBe false

            viewModel.commons().collect()

            expectNoEvents()

            cancelAndIgnoreRemainingEvents()
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


        viewModel.commons().test {
            awaitItem() shouldBe listOf(commonSection1, commonSection2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    should("failed fetch") {

        every {
            cvRepository.commons()
        } returns flow { throw RuntimeException() }


        viewModel.commons().test {
            awaitItem() shouldBe listOf()
            cancelAndIgnoreRemainingEvents()
        }
    }

})
