package com.neocaptainnemo.cv.ui.common

import com.neocaptainnemo.cv.RxTestRule
import com.neocaptainnemo.cv.model.CommonSection
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
class CommonViewModelTest {

    @JvmField
    @Rule
    val rxTestRule = RxTestRule()

    @Mock
    lateinit var dataService: IDataService

    lateinit var viewModel: CommonViewModel

    @Before
    fun setUp() {

        viewModel = CommonViewModel(dataService)
    }

    @Test
    fun `progress during successful fetch`() {

        whenever(dataService.commons()).then { Observable.just(listOf<CommonSection>()) }

        val testable = viewModel.progress.test()

        viewModel.commons().test().assertNoErrors()

        testable.assertValues(false, true, false)
    }


    @Test
    fun `progress during failed fetch`() {

        whenever(dataService.commons()).then { Observable.error<List<CommonSection>>(RuntimeException()) }

        val testable = viewModel.progress.test()

        viewModel.commons().test().assertNoErrors()

        testable.assertValues(false, true, false)
    }

    @Test
    fun `empty state`() {
        whenever(dataService.commons()).then { Observable.just(listOf<CommonSection>()) }

        val testable = viewModel.empty.test()

        viewModel.commons().test().assertNoErrors()

        testable.assertValues(false, true)

    }

    @Test
    fun `not empty state`() {
        whenever(dataService.commons()).then { Observable.just(listOf(CommonSection(), CommonSection())) }

        val testable = viewModel.empty.test()

        viewModel.commons().test().assertNoErrors()

        testable.assertValues(false, false)

    }

    @Test
    fun `successful fetch`() {

        val commonSection1 = CommonSection()
        val commonSection2 = CommonSection()

        whenever(dataService.commons()).then { Observable.just(listOf(commonSection1, commonSection2)) }

        viewModel.commons().test().assertValue(listOf(commonSection1, commonSection2))
    }


    @Test
    fun `failed fetch`() {

        whenever(dataService.commons()).then { Observable.error<List<CommonSection>>(RuntimeException()) }

        viewModel.commons().test().assertValue(listOf())
    }

}