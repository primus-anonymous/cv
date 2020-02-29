package com.neocaptainnemo.cv.ui.projects

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.ui.TestCoroutineRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    val instantExecutorRule = InstantTaskExecutorRule()

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

        val project = Project(name = "Name", storeUrl = "www.some.com")

        viewModel = ProjectDetailsViewModel(app, project)

        assertThat(viewModel.shareUrl).isEqualTo("Project Name www.some.com")
    }

    @Test
    fun `testing share url with github url`() {

        whenever(app.getString(R.string.project)).doAnswer { "Project" }
        whenever(app.getString(R.string.code)).doAnswer { "Code" }

        val project = Project(name = "Name", gitHub = "github.com")

        viewModel = ProjectDetailsViewModel(app, project)

        assertThat(viewModel.shareUrl).isEqualTo("Project Name Code github.com")
    }

    @Test
    fun `testing share url with store and github url`() {

        whenever(app.getString(R.string.project)).doAnswer { "Project" }
        whenever(app.getString(R.string.code)).doAnswer { "Code" }

        val project = Project(name = "Name", storeUrl = "www.some.com", gitHub = "github.com")

        viewModel = ProjectDetailsViewModel(app, project)

        assertThat(viewModel.shareUrl).isEqualTo("Project Name www.some.com Code github.com")
    }

    @Test
    fun `store should be visible`() {

        val project = Project(storeUrl = "www.some.com")

        viewModel = ProjectDetailsViewModel(app, project)

        val visibilityMock = mock<Observer<Boolean>>()
        viewModel.storeVisibility.observeForever(visibilityMock)

        verify(visibilityMock).onChanged(true)

    }

    @Test
    fun `store should be not visible`() {

        val project = Project(storeUrl = "")

        viewModel = ProjectDetailsViewModel(app, project)

        val visibilityMock = mock<Observer<Boolean>>()
        viewModel.storeVisibility.observeForever(visibilityMock)

        verify(visibilityMock).onChanged(false)

    }

    @Test
    fun `github should be visible`() {

        val project = Project(gitHub = "www.some.com")

        viewModel = ProjectDetailsViewModel(app, project)

        val visibilityMock = mock<Observer<Boolean>>()
        viewModel.gitHubVisibility.observeForever(visibilityMock)

        verify(visibilityMock).onChanged(true)

    }

    @Test
    fun `github should be not visible`() {

        val project = Project(gitHub = "")

        viewModel = ProjectDetailsViewModel(app, project)

        val visibilityMock = mock<Observer<Boolean>>()
        viewModel.gitHubVisibility.observeForever(visibilityMock)

        verify(visibilityMock).onChanged(false)

    }

    @Test
    fun `platform image for android`() {

        val project = Project(platform = Project.PLATFORM_ANDROID)

        viewModel = ProjectDetailsViewModel(app, project)

        val imageMock = mock<Observer<Int>>()
        viewModel.platformImage.observeForever(imageMock)

        verify(imageMock).onChanged(R.drawable.ic_android)
    }

    @Test
    fun `platform image for iOS`() {

        val project = Project(platform = Project.PLATFORM_IOS)

        viewModel = ProjectDetailsViewModel(app, project)

        val imageMock = mock<Observer<Int>>()
        viewModel.platformImage.observeForever(imageMock)

        verify(imageMock).onChanged(R.drawable.ic_apple)
    }

    @Test
    fun `web pic`() {

        val project = Project(webPic = "picurl")

        viewModel = ProjectDetailsViewModel(app, project)

        val picMock = mock<Observer<String>>()
        viewModel.webPic.observeForever(picMock)

        verify(picMock).onChanged("picurl")
    }

    @Test
    fun `cover pic`() {

        val project = Project(coverPic = "coverpic")

        viewModel = ProjectDetailsViewModel(app, project)

        val picMock = mock<Observer<String>>()
        viewModel.coverPic.observeForever(picMock)

        verify(picMock).onChanged("coverpic")
    }

    @Test
    fun `project name`() {

        val project = Project(name = "name")

        viewModel = ProjectDetailsViewModel(app, project)

        val nameMock = mock<Observer<String>>()
        viewModel.projectName.observeForever(nameMock)

        verify(nameMock).onChanged("name")
    }

    @Test
    fun stack() {

        val project = Project(stack = "stack")

        viewModel = ProjectDetailsViewModel(app, project)

        val stackMock = mock<Observer<String>>()
        viewModel.stack.observeForever(stackMock)

        verify(stackMock).onChanged("stack")
    }

    @Test
    fun company() {

        val project = Project(vendor = "company")

        viewModel = ProjectDetailsViewModel(app, project)

        val companyMock = mock<Observer<String>>()
        viewModel.company.observeForever(companyMock)

        verify(companyMock).onChanged("company")
    }

    @Test
    fun duties() {

        val project = Project(duties = "duties")

        viewModel = ProjectDetailsViewModel(app, project)

        val dutiesMock = mock<Observer<String>>()
        viewModel.duties.observeForever(dutiesMock)

        verify(dutiesMock).onChanged("duties")
    }

    @Test
    fun `details description`() {

        val project = Project(description = "description")

        viewModel = ProjectDetailsViewModel(app, project)

        val detailsMock = mock<Observer<String>>()
        viewModel.detailsDescription.observeForever(detailsMock)

        verify(detailsMock).onChanged("description")
    }

    @Test
    fun `source code`() {

        val project = Project(gitHub = "githuburl")

        viewModel = ProjectDetailsViewModel(app, project)

        val sourceCodeMock = mock<Observer<String>>()
        viewModel.sourceCode.observeForever(sourceCodeMock)

        verify(sourceCodeMock).onChanged("githuburl")
    }
}

