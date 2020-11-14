package com.neocaptainnemo.cv.ui.common

import com.neocaptainnemo.cv.core.data.DataService
import com.neocaptainnemo.cv.core.model.CommonSection
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
class CommonViewModelTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Mock
    lateinit var dataService: DataService

    lateinit var viewModel: CommonViewModel

    @Before
    fun setUp() {

        viewModel = CommonViewModel(dataService)
    }

    @Test
    fun `progress during successful fetch`() = runBlockingTest {

        whenever(dataService.commons()).doAnswer { flowOf(listOf()) }

        val progressValues = mutableListOf<Boolean>()
        val progressJob = launch {
            viewModel.progress
                    .collect { progressValues.add(it) }
        }

        val commonValuesJob = launch {
            viewModel.commons()
                    .collect { }
        }

        assertThat(progressValues)
                .isEqualTo(listOf(false,
                                  true,
                                  false))
        progressJob.cancel()
        commonValuesJob.cancel()
    }

    @Test
    fun `progress during failed fetch`() = runBlockingTest {

        whenever(dataService.commons()).doAnswer { flow { throw RuntimeException() } }

        val progressValues = mutableListOf<Boolean>()
        val progressJob = launch {
            viewModel.progress
                    .collect { progressValues.add(it) }
        }

        val commonValuesJob = launch {
            viewModel.commons()
                    .collect { }
        }

        assertThat(progressValues)
                .isEqualTo(listOf(false,
                                  true,
                                  false))

        progressJob.cancel()
        commonValuesJob.cancel()
    }

    @Test
    fun `empty state`() = runBlockingTest {
        whenever(dataService.commons()).doAnswer { flowOf(listOf()) }

        val emptyValues = mutableListOf<Boolean>()
        val emptyJob = launch {
            viewModel.empty
                    .collect { emptyValues.add(it) }
        }

        val commonValuesJob = launch {
            viewModel.commons()
                    .collect { }
        }

        assertThat(emptyValues)
                .isEqualTo(listOf(false,
                                  true))

        emptyJob.cancel()
        commonValuesJob.cancel()
    }


    @Test
    fun `not empty state`() = runBlockingTest {
        whenever(dataService.commons()).doAnswer {
            flowOf(listOf(CommonSection(),
                          CommonSection()))
        }

        val emptyValues = mutableListOf<Boolean>()
        val emptyJob = launch {
            viewModel.empty
                    .collect { emptyValues.add(it) }
        }

        val commonValuesJob = launch {
            viewModel.commons()
                    .collect { }
        }

        assertThat(emptyValues)
                .isEqualTo(listOf(false,
                                  false))

        emptyJob.cancel()
        commonValuesJob.cancel()
    }


    @Test
    fun `successful fetch`() = runBlockingTest {

        val commonSection1 = CommonSection()
        val commonSection2 = CommonSection()

        whenever(dataService.commons()).doAnswer {
            flowOf(listOf(commonSection1,
                          commonSection2))
        }

        val commonSectionValues = mutableListOf<List<CommonSection>>()

        val commonValuesJob = launch {
            viewModel.commons()
                    .collect { commonSectionValues.add(it) }
        }

        assertThat(commonSectionValues)
                .isEqualTo(
                        listOf(listOf(commonSection1,
                                      commonSection2)))

        commonValuesJob.cancel()
    }


    @Test
    fun `failed fetch`() = runBlockingTest {

        whenever(dataService.commons()).doAnswer { flow { throw RuntimeException() } }

        val commonSectionValues = mutableListOf<List<CommonSection>>()

        val commonValuesJob = launch {
            viewModel.commons()
                    .collect { commonSectionValues.add(it) }
        }

        assertThat(commonSectionValues)
                .isEqualTo(
                        listOf(listOf<CommonSection>()))

        commonValuesJob.cancel()
    }
}