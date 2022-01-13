package com.neocaptainnemo.cv.ui.projects

import android.app.Application
import app.cash.turbine.test
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.data.CvRepository
import com.neocaptainnemo.cv.core.model.Project
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class ProjectDetailsViewModelTest : ShouldSpec({

    val app: Application = mockk()

    val cvRepository: CvRepository = mockk()

    lateinit var viewModel: ProjectDetailsViewModel

    beforeTest {
        Dispatchers.setMain(StandardTestDispatcher())

        viewModel = ProjectDetailsViewModel(cvRepository, app)
    }

    afterTest {
        clearAllMocks(true)
    }

    should("progress during successful fetch") {

        every {
            cvRepository.project("id")
        } returns flowOf(Project())

        launch {
            viewModel.progress.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("progress during failed fetch") {

        every {
            cvRepository.project("id")
        } throws RuntimeException()

        launch {
            viewModel.progress.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("testing share url with only name") {

        every {
            app.getString(R.string.project)
        } returns "Project"

        val project = Project(name = "Name")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {
            viewModel.shareUrl.test {
                awaitItem() shouldBe "Project Name"

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("testing share url with store url") {

        every {
            app.getString(R.string.project)
        } returns "Project"

        val project = Project(
            name = "Name",
            storeUrl = "www.some.com"
        )

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {
            viewModel.shareUrl.test {
                awaitItem() shouldBe "Project Name www.some.com"

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("testing share url with github url") {

        every {
            app.getString(R.string.project)
        } returns "Project"

        every {
            app.getString(R.string.code)
        } returns "Code"

        val project = Project(
            name = "Name",
            gitHub = "github.com"
        )

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {
            viewModel.shareUrl.test {
                awaitItem() shouldBe "Project Name Code github.com"

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("testing share url with store and github url") {

        every {
            app.getString(R.string.project)
        } returns "Project"

        every {
            app.getString(R.string.code)
        } returns "Code"

        val project = Project(
            name = "Name",
            storeUrl = "www.some.com",
            gitHub = "github.com"
        )

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {
            viewModel.shareUrl.test {
                awaitItem() shouldBe "Project Name www.some.com Code github.com"

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("store should be visible") {

        val project = Project(storeUrl = "www.some.com")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.gitHubVisibility.test {
                awaitItem() shouldBe true
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("store should be not visible") {

        val project = Project(storeUrl = "")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.storeVisibility.test {
                awaitItem() shouldBe false
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("github should be visible") {

        val project = Project(gitHub = "www.some.com")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.gitHubVisibility.test {
                awaitItem() shouldBe true
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("github should be not visible") {

        val project = Project(gitHub = "")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.gitHubVisibility.test {
                awaitItem() shouldBe false
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("platform image for android") {

        val project = Project(platform = Project.PLATFORM_ANDROID)

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.platformImage.test {
                awaitItem() shouldBe R.drawable.ic_android
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("platform image for iOS") {

        val project = Project(platform = Project.PLATFORM_IOS)

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.platformImage.test {
                awaitItem() shouldBe R.drawable.ic_apple
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("web pic") {

        val project = Project(webPic = "picurl")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.webPic.test {
                awaitItem() shouldBe "picurl"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("cover pic") {

        val project = Project(coverPic = "coverpic")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.coverPic.test {
                awaitItem() shouldBe "coverpic"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("project name") {

        val project = Project(name = "name")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.projectName.test {
                awaitItem() shouldBe "name"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("stack") {

        val project = Project(stack = "stack")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.stack.test {
                awaitItem() shouldBe "stack"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("company") {

        val project = Project(vendor = "company")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.company.test {
                awaitItem() shouldBe "company"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("duties") {

        val project = Project(duties = "duties")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.duties.test {
                awaitItem() shouldBe "duties"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("details description") {

        val project = Project(description = "description")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.detailsDescription.test {
                awaitItem() shouldBe "description"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }

    should("source code") {

        val project = Project(gitHub = "githuburl")

        every {
            cvRepository.project("id")
        } returns flowOf(project)

        launch {

            viewModel.sourceCode.test {
                awaitItem() shouldBe "githuburl"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchProject("id")
        }
    }
})
