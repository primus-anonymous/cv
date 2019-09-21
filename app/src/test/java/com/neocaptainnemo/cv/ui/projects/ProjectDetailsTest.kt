package com.neocaptainnemo.cv.ui.projects

import android.app.Application
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.RxTestRule
import com.neocaptainnemo.cv.model.Project
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProjectDetailsTest {

    @Mock
    lateinit var app: Application

    lateinit var viewModel: ProjectDetailsViewModel

    @JvmField
    @Rule
    val rxTestRule = RxTestRule()

    @Test
    fun `testing share url with only name`() {

        whenever(app.getString(R.string.project)).thenReturn("Project")

        val project = Project("Name")

        viewModel = ProjectDetailsViewModel(app, project)

        assertThat(viewModel.shareUrl).isEqualTo("Project Name")
    }

    @Test
    fun `testing share url with store url`() {

        whenever(app.getString(R.string.project)).thenReturn("Project")

        val project = Project("Name", storeUrl = "www.some.com")

        viewModel = ProjectDetailsViewModel(app, project)

        assertThat(viewModel.shareUrl).isEqualTo("Project Name www.some.com")
    }

    @Test
    fun `testing share url with github url`() {

        whenever(app.getString(R.string.project)).thenReturn("Project")
        whenever(app.getString(R.string.code)).thenReturn("Code")

        val project = Project("Name", gitHub = "github.com")

        viewModel = ProjectDetailsViewModel(app, project)

        assertThat(viewModel.shareUrl).isEqualTo("Project Name Code github.com")
    }

    @Test
    fun `testing share url with store and github url`() {

        whenever(app.getString(R.string.project)).thenReturn("Project")
        whenever(app.getString(R.string.code)).thenReturn("Code")

        val project = Project("Name", storeUrl = "www.some.com", gitHub = "github.com")

        viewModel = ProjectDetailsViewModel(app, project)

        assertThat(viewModel.shareUrl).isEqualTo("Project Name www.some.com Code github.com")
    }

    @Test
    fun `store should be visible`() {

        val project = Project(storeUrl = "www.some.com")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.storeVisibility.test().assertValue(true)

    }

    @Test
    fun `store should be not visible`() {

        val project = Project(storeUrl = "")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.storeVisibility.test().assertValue(false)

    }

    @Test
    fun `github should be visible`() {

        val project = Project(gitHub = "www.some.com")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.gitHubVisibility.test().assertValue(true)

    }

    @Test
    fun `github should be not visible`() {

        val project = Project(gitHub = "")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.gitHubVisibility.test().assertValue(false)

    }

    @Test
    fun `platform image for android`() {

        val project = Project(platform = Project.PLATFORM_ANDROID)

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.platformImage.test().assertValue(R.drawable.ic_android)
    }

    @Test
    fun `platform image for iOS`() {

        val project = Project(platform = Project.PLATFORM_IOS)

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.platformImage.test().assertValue(R.drawable.ic_apple)
    }

    @Test
    fun `web pic`() {

        val project = Project(webPic = "picurl")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.webPic.test().assertValue("picurl")
    }

    @Test
    fun `cover pic`() {

        val project = Project(coverPic = "coverpic")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.coverPic.test().assertValue("coverpic")
    }

    @Test
    fun `project name`() {

        val project = Project(name = "name")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.projectName.test().assertValue("name")
    }

    @Test
    fun stack() {

        val project = Project(stack = "stack")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.stack.test().assertValue("stack")
    }

    @Test
    fun company() {

        val project = Project(vendor = "company")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.company.test().assertValue("company")
    }

    @Test
    fun duties() {

        val project = Project(duties = "duties")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.duties.test().assertValue("duties")
    }

    @Test
    fun `details description`() {

        val project = Project(description = "description")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.detailsDescription.test().assertValue("description")
    }

    @Test
    fun `source code`() {

        val project = Project(gitHub = "githuburl")

        viewModel = ProjectDetailsViewModel(app, project)

        viewModel.sourceCode.test().assertValue("githuburl")
    }
}

