package com.neocaptainnemo.cv.ui.projects

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.neocaptainnemo.cv.model.Filter
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.IDataService
import com.neocaptainnemo.cv.ui.TestCoroutineRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProjectsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Mock
    lateinit var dataService: IDataService

    lateinit var viewModel: ProjectsViewModel

    @Before
    fun setUp() {
        viewModel = ProjectsViewModel(dataService)
    }

    @Test
    fun `progress during successful fetch`() {

        whenever(dataService.projects()).doAnswer { flowOf(listOf()) }

        val progressObserver = mock<Observer<Boolean>>()
        val order = inOrder(progressObserver, progressObserver, progressObserver)

        viewModel.progress.observeForever(progressObserver)

        viewModel.projects().observeForever { }

        order.verify(progressObserver).onChanged(false)
        order.verify(progressObserver).onChanged(true)
        order.verify(progressObserver).onChanged(false)
    }

    @Test
    fun `progress during failed fetch`() {

        whenever(dataService.projects()).doAnswer { flow { throw RuntimeException() } }

        val progressObserver = mock<Observer<Boolean>>()
        val order = inOrder(progressObserver, progressObserver, progressObserver)

        viewModel.progress.observeForever(progressObserver)

        viewModel.projects().observeForever { }

        order.verify(progressObserver).onChanged(false)
        order.verify(progressObserver).onChanged(true)
        order.verify(progressObserver).onChanged(false)
    }

    @Test
    fun `empty state`() {
        whenever(dataService.projects()).doAnswer { flowOf(listOf()) }

        val emptyObserver = mock<Observer<Boolean>>()
        viewModel.empty.observeForever(emptyObserver)

        val order = inOrder(emptyObserver, emptyObserver)

        viewModel.projects().observeForever { }

        order.verify(emptyObserver).onChanged(false)
        order.verify(emptyObserver).onChanged(true)
    }

    @Test
    fun `not empty state`() {
        whenever(dataService.projects()).doAnswer { flowOf(listOf(Project(), Project())) }

        val emptyObserver = mock<Observer<Boolean>>()
        viewModel.empty.observeForever(emptyObserver)

        viewModel.projects().observeForever { }

        verify(emptyObserver, times(2)).onChanged(false)
    }


    @Test
    fun `filters set to all`() {

        val project1 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        val project2 = Project().apply {
            platform = Project.PLATFORM_IOS
        }

        val project3 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        whenever(dataService.projects()).doAnswer { flowOf(listOf(project1, project2, project3)) }

        viewModel.filter = Filter.ALL

        val projectsObserver = mock<Observer<List<Project>>>()
        viewModel.projects().observeForever(projectsObserver)

        verify(projectsObserver).onChanged(listOf(project1, project2, project3))
    }


    @Test
    fun `filters only android`() {

        val project1 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        val project2 = Project().apply {
            platform = Project.PLATFORM_IOS
        }

        val project3 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        whenever(dataService.projects()).doAnswer { flowOf(listOf(project1, project2, project3)) }

        viewModel.filter = Filter.ANDROID

        val projectsObserver = mock<Observer<List<Project>>>()
        viewModel.projects().observeForever(projectsObserver)

        verify(projectsObserver).onChanged(listOf(project1, project3))
    }

    @Test
    fun `filters only iOS`() {

        val project1 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        val project2 = Project().apply {
            platform = Project.PLATFORM_IOS
        }

        val project3 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        whenever(dataService.projects()).doAnswer { flowOf(listOf(project1, project2, project3)) }

        viewModel.filter = Filter.IOS

        val projectsObserver = mock<Observer<List<Project>>>()
        viewModel.projects().observeForever(projectsObserver)

        verify(projectsObserver).onChanged(listOf(project2))
    }


    @Test
    fun `successful fetch`() {

        val project1 = Project()
        val project2 = Project()

        whenever(dataService.projects()).doAnswer { flowOf(listOf(project1, project2)) }

        val projectsObserver = mock<Observer<List<Project>>>()
        viewModel.projects().observeForever(projectsObserver)

        verify(projectsObserver).onChanged(listOf(project1, project2))
    }


    @Test
    fun `failed fetch`() {

        whenever(dataService.projects()).doAnswer { flow { throw RuntimeException() } }

        val projectsObserver = mock<Observer<List<Project>>>()
        viewModel.projects().observeForever(projectsObserver)

        verify(projectsObserver).onChanged(listOf())
    }
}