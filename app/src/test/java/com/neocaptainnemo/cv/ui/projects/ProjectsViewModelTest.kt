package com.neocaptainnemo.cv.ui.projects

import com.neocaptainnemo.cv.core.data.DataService
import com.neocaptainnemo.cv.core.model.Filter
import com.neocaptainnemo.cv.core.model.Project
import com.neocaptainnemo.cv.ui.TestCoroutineRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProjectsViewModelTest {


    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Mock
    lateinit var dataService: DataService

    lateinit var viewModel: ProjectsViewModel

    @Before
    fun setUp() {
        viewModel = ProjectsViewModel(dataService)
    }

    @Test
    fun `progress during successful fetch`() = runBlockingTest {

        whenever(dataService.projects()).doAnswer { flowOf(listOf()) }

        val progressValues = mutableListOf<Boolean>()

        val progressJob = launch {
            viewModel.progress.collect {
                progressValues.add(it)
            }
        }

        val projectsJob = launch {
            viewModel.projects()
                    .collect { }
        }

        assertThat(progressValues)
                .isEqualTo(
                        listOf(false,
                               true,
                               false))

        progressJob.cancel()
        projectsJob.cancel()
    }

    @Test
    fun `progress during failed fetch`() = runBlockingTest {

        whenever(dataService.projects()).doAnswer { flow { throw RuntimeException() } }

        val progressValues = mutableListOf<Boolean>()

        val progressJob = launch {
            viewModel.progress.collect {
                progressValues.add(it)
            }
        }

        val projectsJob = launch {
            viewModel.projects()
                    .collect { }
        }

        assertThat(progressValues)
                .isEqualTo(
                        listOf(false,
                               true,
                               false))

        progressJob.cancel()
        projectsJob.cancel()
    }

    @Test
    fun `empty state`() = runBlockingTest {
        whenever(dataService.projects()).doAnswer { flowOf(listOf()) }

        val emptyValues = mutableListOf<Boolean>()

        val emptyJob = launch {
            viewModel.empty.collect {
                emptyValues.add(it)
            }
        }

        val projectsJob = launch {
            viewModel.projects()
                    .collect { }
        }

        assertThat(emptyValues)
                .isEqualTo(
                        listOf(false,
                               true))

        emptyJob.cancel()
        projectsJob.cancel()
    }

    @Test
    fun `not empty state`() = runBlockingTest {
        whenever(dataService.projects()).doAnswer {
            flowOf(listOf(Project(),
                          Project()))
        }

        val emptyValues = mutableListOf<Boolean>()

        val emptyJob = launch {
            viewModel.empty.collect {
                emptyValues.add(it)
            }
        }

        val projectsJob = launch {
            viewModel.projects()
                    .collect { }
        }

        assertThat(emptyValues)
                .isEqualTo(
                        listOf(false,
                               false))

        emptyJob.cancel()
        projectsJob.cancel()
    }


    @Test
    fun `filters set to all`() = runBlockingTest {

        val project1 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        val project2 = Project().apply {
            platform = Project.PLATFORM_IOS
        }

        val project3 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        whenever(dataService.projects()).doAnswer {
            flowOf(listOf(project1,
                          project2,
                          project3))
        }

        viewModel.filter = Filter.ALL

        val projectValues = mutableListOf<List<Project>>()

        val projectsJob = launch {
            viewModel.projects()
                    .collect {
                        projectValues.add(it)
                    }
        }

        assertThat(projectValues)
                .isEqualTo(
                        listOf(listOf(project1,
                                      project2,
                                      project3)))

        projectsJob.cancel()
    }


    @Test
    fun `filters only android`() = runBlockingTest {

        val project1 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        val project2 = Project().apply {
            platform = Project.PLATFORM_IOS
        }

        val project3 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        whenever(dataService.projects()).doAnswer {
            flowOf(listOf(project1,
                          project2,
                          project3))
        }

        viewModel.filter = Filter.ANDROID

        val projectValues = mutableListOf<List<Project>>()

        val projectsJob = launch {
            viewModel.projects()
                    .collect {
                        projectValues.add(it)
                    }
        }

        assertThat(projectValues)
                .isEqualTo(
                        listOf(listOf(project1,
                                      project3)))

        projectsJob.cancel()
    }

    @Test
    fun `filters only iOS`() = runBlockingTest {

        val project1 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        val project2 = Project().apply {
            platform = Project.PLATFORM_IOS
        }

        val project3 = Project().apply {
            platform = Project.PLATFORM_ANDROID
        }

        whenever(dataService.projects()).doAnswer {
            flowOf(listOf(project1,
                          project2,
                          project3))
        }

        viewModel.filter = Filter.IOS

        val projectValues = mutableListOf<List<Project>>()

        val projectsJob = launch {
            viewModel.projects()
                    .collect {
                        projectValues.add(it)
                    }
        }

        assertThat(projectValues)
                .isEqualTo(
                        listOf(listOf(project2)))

        projectsJob.cancel()
    }


    @Test
    fun `successful fetch`() = runBlockingTest {

        val project1 = Project()
        val project2 = Project()

        whenever(dataService.projects()).doAnswer {
            flowOf(listOf(project1,
                          project2))
        }

        val projectValues = mutableListOf<List<Project>>()

        val projectsJob = launch {
            viewModel.projects()
                    .collect {
                        projectValues.add(it)
                    }
        }

        assertThat(projectValues)
                .isEqualTo(
                        listOf(listOf(project1,
                                      project2)))

        projectsJob.cancel()
    }


    @Test
    fun `failed fetch`() = runBlockingTest {

        whenever(dataService.projects()).doAnswer { flow { throw RuntimeException() } }

        val projectValues = mutableListOf<List<Project>>()

        val projectsJob = launch {
            viewModel.projects()
                    .collect {
                        projectValues.add(it)
                    }
        }

        assertThat(projectValues)
                .isEqualTo(
                        listOf(listOf<Project>()))

        projectsJob.cancel()
    }
}