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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class ProjectDetailsViewModelTest : ShouldSpec({

    val app: Application = mockk()

    val cvRepository: CvRepository = mockk()

    lateinit var viewModel: ProjectDetailsViewModel

    beforeTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel = ProjectDetailsViewModel(cvRepository, app)
    }

    afterTest {
        clearAllMocks(true)
    }

    should("progress during successful fetch") {

        Dispatchers.setMain(Dispatchers.Default)

        every {
            cvRepository.project("id")
        } returns flowOf(Project())

        viewModel.progress.test {
            awaitItem() shouldBe false

            viewModel.fetchProject("id")

            awaitItem() shouldBe true
            awaitItem() shouldBe false

            cancelAndIgnoreRemainingEvents()
        }

    }

    should("progress during failed fetch") {

        Dispatchers.setMain(Dispatchers.Default)

        every {
            cvRepository.project("id")
        } returns flow {
            throw RuntimeException()
        }

        viewModel.progress.test {
            awaitItem() shouldBe false

            viewModel.fetchProject("id")

            awaitItem() shouldBe true
            awaitItem() shouldBe false

            cancelAndIgnoreRemainingEvents()
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

        viewModel.shareUrl.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "Project Name"

            cancelAndIgnoreRemainingEvents()
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

        viewModel.shareUrl.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "Project Name www.some.com"

            cancelAndIgnoreRemainingEvents()
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

        viewModel.shareUrl.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "Project Name Code github.com"

            cancelAndIgnoreRemainingEvents()
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

        viewModel.shareUrl.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "Project Name www.some.com Code github.com"

            cancelAndIgnoreRemainingEvents()
        }

    }

    should("store should be visible") {

        Dispatchers.setMain(Dispatchers.Default)

        val project = Project(storeUrl = "www.some.com")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.storeVisibility.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe true
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("store should be not visible") {

        val project = Project(storeUrl = "")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.storeVisibility.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe false
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("github should be visible") {

        val project = Project(gitHub = "www.some.com")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.gitHubVisibility.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe true
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("github should be not visible") {

        val project = Project(gitHub = "")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.gitHubVisibility.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe false
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("platform image for android") {

        val project = Project(platform = Project.PLATFORM_ANDROID)

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.platformImage.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe R.drawable.ic_android
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("platform image for iOS") {

        val project = Project(platform = Project.PLATFORM_IOS)

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.platformImage.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe R.drawable.ic_apple
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("web pic") {

        val project = Project(webPic = "picurl")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.webPic.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "picurl"
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("cover pic") {

        val project = Project(coverPic = "coverpic")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.coverPic.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "coverpic"
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("project name") {

        val project = Project(name = "name")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.projectName.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "name"
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("stack") {

        val project = Project(stack = "stack")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.stack.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "stack"
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("company") {

        val project = Project(vendor = "company")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.company.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "company"
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("duties") {

        val project = Project(duties = "duties")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.duties.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "duties"
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("details description") {

        val project = Project(description = "description")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.detailsDescription.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "description"
            cancelAndIgnoreRemainingEvents()
        }

    }

    should("source code") {

        val project = Project(gitHub = "githuburl")

        every {
            cvRepository.project("id")
        } returns flowOf(project)


        viewModel.sourceCode.test {
            viewModel.fetchProject("id")

            awaitItem() shouldBe "githuburl"
            cancelAndIgnoreRemainingEvents()
        }

    }
})
