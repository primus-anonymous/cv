package com.neocaptainnemo.cv.ui.projects

import android.app.Application
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.model.Project
import com.neocaptainnemo.cv.ui.TestCoroutineRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProjectDetailsTest {

    @Mock
    lateinit var app: Application

    lateinit var viewModel: ProjectDetailsViewModel

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Test
    fun `testing share url with only name`() {

        whenever(app.getString(R.string.project)).doAnswer { "Project" }

        val project = Project(name = "Name")

        viewModel = ProjectDetailsViewModel(app, project)

        assertThat(viewModel.shareUrl).isEqualTo("Project Name")
    }

    @Test
    fun `testing share url with store url`() {

        whenever(app.getString(R.string.project)).doAnswer { "Project" }

        val project = Project(
            name = "Name",
            storeUrl = "www.some.com"
        )

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        assertThat(viewModel.shareUrl).isEqualTo("Project Name www.some.com")
    }

    @Test
    fun `testing share url with github url`() {

        whenever(app.getString(R.string.project)).doAnswer { "Project" }
        whenever(app.getString(R.string.code)).doAnswer { "Code" }

        val project = Project(
            name = "Name",
            gitHub = "github.com"
        )

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        assertThat(viewModel.shareUrl).isEqualTo("Project Name Code github.com")
    }

    @Test
    fun `testing share url with store and github url`() {

        whenever(app.getString(R.string.project)).doAnswer { "Project" }
        whenever(app.getString(R.string.code)).doAnswer { "Code" }

        val project = Project(
            name = "Name",
            storeUrl = "www.some.com",
            gitHub = "github.com"
        )

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        assertThat(viewModel.shareUrl).isEqualTo("Project Name www.some.com Code github.com")
    }

    @Test
    fun `store should be visible`() = runBlockingTest {

        val project = Project(storeUrl = "www.some.com")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val storeVisibilityValues = mutableListOf<Boolean>()

        val storeVisJob = launch {
            viewModel.storeVisibility.collect {
                storeVisibilityValues.add(it)
            }
        }

        assertThat(storeVisibilityValues)
            .isEqualTo(
                listOf(true)
            )

        storeVisJob.cancel()
    }

    @Test
    fun `store should be not visible`() = runBlockingTest {

        val project = Project(storeUrl = "")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val storeVisibilityValues = mutableListOf<Boolean>()

        val storeVisJob = launch {
            viewModel.storeVisibility.collect {
                storeVisibilityValues.add(it)
            }
        }

        assertThat(storeVisibilityValues)
            .isEqualTo(
                listOf(false)
            )

        storeVisJob.cancel()
    }

    @Test
    fun `github should be visible`() = runBlockingTest {

        val project = Project(gitHub = "www.some.com")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val githubVisibilityValues = mutableListOf<Boolean>()

        val githubVisJob = launch {
            viewModel.gitHubVisibility.collect {
                githubVisibilityValues.add(it)
            }
        }

        assertThat(githubVisibilityValues)
            .isEqualTo(
                listOf(true)
            )

        githubVisJob.cancel()
    }

    @Test
    fun `github should be not visible`() = runBlockingTest {

        val project = Project(gitHub = "")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val githubVisibilityValues = mutableListOf<Boolean>()

        val githubVisJob = launch {
            viewModel.gitHubVisibility.collect {
                githubVisibilityValues.add(it)
            }
        }

        assertThat(githubVisibilityValues)
            .isEqualTo(
                listOf(false)
            )

        githubVisJob.cancel()
    }

    @Test
    fun `platform image for android`() = runBlockingTest {

        val project = Project(platform = Project.PLATFORM_ANDROID)

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val platformImageValues = mutableListOf<Int>()

        val platformImageJob = launch {
            viewModel.platformImage.collect {
                platformImageValues.add(it)
            }
        }

        assertThat(platformImageValues)
            .isEqualTo(
                listOf(R.drawable.ic_android)
            )

        platformImageJob.cancel()
    }

    @Test
    fun `platform image for iOS`() = runBlockingTest {

        val project = Project(platform = Project.PLATFORM_IOS)

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val platformImageValues = mutableListOf<Int>()

        val platformImageJob = launch {
            viewModel.platformImage.collect {
                platformImageValues.add(it)
            }
        }

        assertThat(platformImageValues)
            .isEqualTo(
                listOf(R.drawable.ic_apple)
            )

        platformImageJob.cancel()
    }

    @Test
    fun `web pic`() = runBlockingTest {

        val project = Project(webPic = "picurl")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val webPicValues = mutableListOf<String>()

        val webPicJob = launch {
            viewModel.webPic.collect {
                webPicValues.add(it)
            }
        }

        assertThat(webPicValues)
            .isEqualTo(
                listOf("picurl")
            )

        webPicJob.cancel()
    }

    @Test
    fun `cover pic`() = runBlockingTest {

        val project = Project(coverPic = "coverpic")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val coverPicValues = mutableListOf<String>()

        val coverPicJob = launch {
            viewModel.coverPic.collect {
                coverPicValues.add(it)
            }
        }

        assertThat(coverPicValues)
            .isEqualTo(
                listOf("coverpic")
            )

        coverPicJob.cancel()
    }

    @Test
    fun `project name`() = runBlockingTest {

        val project = Project(name = "name")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val projectNameValues = mutableListOf<String>()

        val projectNameJob = launch {
            viewModel.projectName.collect {
                projectNameValues.add(it)
            }
        }

        assertThat(projectNameValues)
            .isEqualTo(
                listOf("name")
            )

        projectNameJob.cancel()
    }

    @Test
    fun stack() = runBlockingTest {

        val project = Project(stack = "stack")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val stackValues = mutableListOf<String>()

        val stackJob = launch {
            viewModel.stack.collect {
                stackValues.add(it)
            }
        }

        assertThat(stackValues)
            .isEqualTo(
                listOf("stack")
            )

        stackJob.cancel()
    }

    @Test
    fun company() = runBlockingTest {

        val project = Project(vendor = "company")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val companyValues = mutableListOf<String>()

        val companyJob = launch {
            viewModel.company.collect {
                companyValues.add(it)
            }
        }

        assertThat(companyValues)
            .isEqualTo(
                listOf("company")
            )

        companyJob.cancel()
    }

    @Test
    fun duties() = runBlockingTest {

        val project = Project(duties = "duties")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val dutiesValues = mutableListOf<String>()

        val dutiesJob = launch {
            viewModel.duties.collect {
                dutiesValues.add(it)
            }
        }

        assertThat(dutiesValues)
            .isEqualTo(
                listOf("duties")
            )

        dutiesJob.cancel()
    }

    @Test
    fun `details description`() = runBlockingTest {

        val project = Project(description = "description")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val detailsDescriptionValues = mutableListOf<String>()

        val detailsDescriptionJob = launch {
            viewModel.detailsDescription.collect {
                detailsDescriptionValues.add(it)
            }
        }

        assertThat(detailsDescriptionValues).isEqualTo(
            listOf("description")
        )

        detailsDescriptionJob.cancel()
    }

    @Test
    fun `source code`() = runBlockingTest {

        val project = Project(gitHub = "githuburl")

        viewModel = ProjectDetailsViewModel(
            app,
            project
        )

        val sourceCodeValues = mutableListOf<String>()

        val sourceCodeJob = launch {
            viewModel.sourceCode.collect {
                sourceCodeValues.add(it)
            }
        }

        assertThat(sourceCodeValues)
            .isEqualTo(
                listOf("githuburl")
            )

        sourceCodeJob.cancel()
    }
}
