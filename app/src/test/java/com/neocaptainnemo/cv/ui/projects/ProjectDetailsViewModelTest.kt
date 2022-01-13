package com.neocaptainnemo.cv.ui.projects

import android.app.Application
import app.cash.turbine.test
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.data.DataService
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

    val dataService: DataService = mockk()

    lateinit var viewModel: ProjectDetailsViewModel

    beforeTest {
        Dispatchers.setMain(StandardTestDispatcher())

        viewModel = ProjectDetailsViewModel(dataService, app)
    }

    afterTest {
        clearAllMocks(true)
    }

    should("progress during successful fetch") {

        every {
            dataService.project("id")
        } returns flowOf(Project())

        launch {
            viewModel.progress.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("progress during failed fetch") {

        every {
            dataService.project("id")
        } throws RuntimeException()

        launch {
            viewModel.progress.test {
                awaitItem() shouldBe false
                awaitItem() shouldBe true
                awaitItem() shouldBe false

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("testing share url with only name") {

        every {
            app.getString(R.string.project)
        } returns "Project"

        val project = Project(name = "Name")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {
            viewModel.shareUrl.test {
                awaitItem() shouldBe "Project Name"

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
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
            dataService.project("id")
        } returns flowOf(project)

        launch {
            viewModel.shareUrl.test {
                awaitItem() shouldBe "Project Name www.some.com"

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
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
            dataService.project("id")
        } returns flowOf(project)

        launch {
            viewModel.shareUrl.test {
                awaitItem() shouldBe "Project Name Code github.com"

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
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
            dataService.project("id")
        } returns flowOf(project)

        launch {
            viewModel.shareUrl.test {
                awaitItem() shouldBe "Project Name www.some.com Code github.com"

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("store should be visible") {

        val project = Project(storeUrl = "www.some.com")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.gitHubVisibility.test {
                awaitItem() shouldBe true
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("store should be not visible") {

        val project = Project(storeUrl = "")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.storeVisibility.test {
                awaitItem() shouldBe false
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("github should be visible") {

        val project = Project(gitHub = "www.some.com")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.gitHubVisibility.test {
                awaitItem() shouldBe true
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("github should be not visible") {

        val project = Project(gitHub = "")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.gitHubVisibility.test {
                awaitItem() shouldBe false
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("platform image for android") {

        val project = Project(platform = Project.PLATFORM_ANDROID)

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.platformImage.test {
                awaitItem() shouldBe R.drawable.ic_android
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("platform image for iOS") {

        val project = Project(platform = Project.PLATFORM_IOS)

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.platformImage.test {
                awaitItem() shouldBe R.drawable.ic_apple
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("web pic") {

        val project = Project(webPic = "picurl")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.webPic.test {
                awaitItem() shouldBe "picurl"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("cover pic") {

        val project = Project(coverPic = "coverpic")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.coverPic.test {
                awaitItem() shouldBe "coverpic"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("project name") {

        val project = Project(name = "name")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.projectName.test {
                awaitItem() shouldBe "name"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("stack") {

        val project = Project(stack = "stack")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.stack.test {
                awaitItem() shouldBe "stack"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("company") {

        val project = Project(vendor = "company")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.company.test {
                awaitItem() shouldBe "company"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("duties") {

        val project = Project(duties = "duties")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.duties.test {
                awaitItem() shouldBe "duties"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("details description") {

        val project = Project(description = "description")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.detailsDescription.test {
                awaitItem() shouldBe "description"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }

    should("source code") {

        val project = Project(gitHub = "githuburl")

        every {
            dataService.project("id")
        } returns flowOf(project)

        launch {

            viewModel.sourceCode.test {
                awaitItem() shouldBe "githuburl"
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.projectDetails("id")
        }
    }
})
