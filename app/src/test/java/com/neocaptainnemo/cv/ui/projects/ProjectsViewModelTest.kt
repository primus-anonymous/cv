package com.neocaptainnemo.cv.ui.projects

import app.cash.turbine.test
import com.neocaptainnemo.cv.core.data.CvRepository
import com.neocaptainnemo.cv.core.model.Filter
import com.neocaptainnemo.cv.core.model.Project
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
class ProjectsViewModelTest : ShouldSpec({

    val cvRepository: CvRepository = mockk()

    lateinit var viewModel: ProjectsViewModel

    beforeTest {
        Dispatchers.setMain(StandardTestDispatcher())

        viewModel = ProjectsViewModel(cvRepository)
    }

    afterTest {
        clearAllMocks(true)
    }

    should("progress during successful fetch") {

        every {
            cvRepository.projects()
        } returns flowOf(listOf())

        launch {
            viewModel.progress.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projects().collect()
        }
    }

    should("progress during failed fetch") {

        every {
            cvRepository.projects()
        } throws RuntimeException()

        launch {
            viewModel.progress.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projects().collect()
        }
    }

    should("empty state") {
        every {
            cvRepository.projects()
        } returns flowOf(listOf())

        launch {
            viewModel.empty.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("not empty state") {
        every {
            cvRepository.projects()
        } returns flowOf(
            listOf(
                Project(),
                Project()
            )
        )

        launch {
            viewModel.empty.test {
                awaitItem() shouldBe false
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("filters set to all") {

        val project1 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        val project2 = Project().apply {
            platform = Project.PLATFORM_IOS
        }

        val project3 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        every {
            cvRepository.projects()
        } returns flowOf(
            listOf(
                project1,
                project2,
                project3
            )
        )

        viewModel.filter = Filter.ALL

        launch {
            viewModel.projects().test {
                awaitItem() shouldBe listOf(
                    project1,
                    project2,
                    project3
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("filters only android") {

        val project1 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        val project2 = Project().apply {
            platform = Project.PLATFORM_IOS
        }

        val project3 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        every {
            cvRepository.projects()
        } returns flowOf(
            listOf(
                project1,
                project2,
                project3
            )
        )

        viewModel.filter = Filter.ANDROID

        launch {
            viewModel.projects().test {
                awaitItem() shouldBe listOf(
                    project1,
                    project3
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("filters only iOS") {

        val project1 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        val project2 = Project().apply {
            platform = Project.PLATFORM_IOS
        }

        val project3 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        every {
            cvRepository.projects()
        } returns flowOf(
            listOf(
                project1,
                project2,
                project3
            )
        )

        viewModel.filter = Filter.IOS

        launch {
            viewModel.projects().test {
                awaitItem() shouldBe listOf(
                    project2,
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("successful fetch") {

        val project1 = Project()
        val project2 = Project()

        every {
            cvRepository.projects()
        } returns flowOf(
            listOf(
                project1,
                project2,
            )
        )

        launch {
            viewModel.projects().test {
                awaitItem() shouldBe listOf(
                    project1,
                    project2,
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    should("failed fetch") {

        every {
            cvRepository.projects()
        } returns flow { throw RuntimeException() }

        launch {
            viewModel.projects().test {
                awaitItem() shouldBe listOf()

                cancelAndIgnoreRemainingEvents()
            }
        }
    }
})
