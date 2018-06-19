package com.neocaptainnemo.cv.ui.projects

import com.neocaptainnemo.cv.RxTestRule
import com.neocaptainnemo.cv.model.Filter
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.IDataService
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ProjectsViewModelTest {

    @JvmField
    @Rule
    val rxRule = RxTestRule()

    @Mock
    lateinit var dataService: IDataService

    lateinit var viewModel: ProjectsViewModel

    @Before
    fun setUp() {
        viewModel = ProjectsViewModel(dataService)
    }

    @Test
    fun progressSuccess() {

        whenever(dataService.projects()).then { Observable.just(listOf<Project>()) }

        val testable = viewModel.progress().test()

        viewModel.projects().test().assertNoErrors()

        testable.assertValues(false, true, false)
    }

    @Test
    fun progressFailure() {

        whenever(dataService.projects()).then { Observable.error<List<Project>>(RuntimeException()) }

        val testable = viewModel.progress().test()

        viewModel.projects().test().assertNoErrors()

        testable.assertValues(false, true, false)
    }

    @Test
    fun empty() {

        whenever(dataService.projects()).then { Observable.just(listOf<Project>()) }

        val testable = viewModel.empty().test()

        viewModel.projects().test().assertNoErrors()

        testable.assertValues(false, true)
    }

    @Test
    fun notEmpty() {

        whenever(dataService.projects()).then { Observable.just(listOf(Project(), Project())) }

        val testable = viewModel.empty().test()

        viewModel.projects().test().assertNoErrors()

        testable.assertValues(false, false)
    }


    @Test
    fun filterAll() {

        val project1 = Project()
        project1.platform = Project.PLATFORM_ANDROID

        val project2 = Project()
        project1.platform = Project.PLATFORM_IOS

        val project3 = Project()
        project1.platform = Project.PLATFORM_ANDROID

        whenever(dataService.projects()).then { Observable.just(listOf(project1, project2, project3)) }

        viewModel.filter = Filter.ALL

        viewModel.projects().test().assertValues(listOf(project1, project2, project3))
    }

    @Test
    fun filterAndroid() {

        val project1 = Project()
        project1.platform = Project.PLATFORM_ANDROID

        val project2 = Project()
        project2.platform = Project.PLATFORM_IOS

        val project3 = Project()
        project3.platform = Project.PLATFORM_ANDROID

        whenever(dataService.projects()).then { Observable.just(listOf(project1, project2, project3)) }

        viewModel.filter = Filter.ANDROID

        viewModel.projects().test().assertValues(listOf(project1, project3))
    }

    @Test
    fun filteriOS() {

        val project1 = Project()
        project1.platform = Project.PLATFORM_ANDROID

        val project2 = Project()
        project2.platform = Project.PLATFORM_IOS

        val project3 = Project()
        project3.platform = Project.PLATFORM_ANDROID

        whenever(dataService.projects()).then { Observable.just(listOf(project1, project2, project3)) }

        viewModel.filter = Filter.IOS

        viewModel.projects().test().assertValues(listOf(project2))
    }

    @Test
    fun success() {

        val project1 = Project()
        val project2 = Project()

        whenever(dataService.projects()).then { Observable.just(listOf(project1, project2)) }

        viewModel.projects().test().assertValue(listOf(project1, project2))
    }


    @Test
    fun failure() {

        whenever(dataService.projects()).then { Observable.error<List<Project>>(RuntimeException()) }

        viewModel.projects().test().assertValue(listOf())

    }

}