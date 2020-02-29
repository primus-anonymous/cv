package com.neocaptainnemo.cv.ui.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.neocaptainnemo.cv.model.CommonSection
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
class CommonViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Mock
    lateinit var dataService: IDataService

    lateinit var viewModel: CommonViewModel

    @Before
    fun setUp() {

        viewModel = CommonViewModel(dataService)
    }

    @Test
    fun `progress during successful fetch`() {

        whenever(dataService.commons()).doAnswer { flowOf(listOf()) }

        val progressObserver = mock<Observer<Boolean>>()
        viewModel.progress.observeForever(progressObserver)

        val order = inOrder(progressObserver, progressObserver, progressObserver)

        viewModel.commons().observeForever { }

        order.verify(progressObserver).onChanged(false)
        order.verify(progressObserver).onChanged(true)
        order.verify(progressObserver).onChanged(false)
    }


    @Test
    fun `progress during failed fetch`() {

        whenever(dataService.commons()).doAnswer { flow { throw RuntimeException() } }

        val progressObserver = mock<Observer<Boolean>>()
        viewModel.progress.observeForever(progressObserver)

        val order = inOrder(progressObserver, progressObserver, progressObserver)

        viewModel.commons().observeForever { }

        order.verify(progressObserver).onChanged(false)
        order.verify(progressObserver).onChanged(true)
        order.verify(progressObserver).onChanged(false)
    }

    @Test
    fun `empty state`() {
        whenever(dataService.commons()).doAnswer { flowOf(listOf()) }

        val emptyObserver = mock<Observer<Boolean>>()
        viewModel.empty.observeForever(emptyObserver)

        val order = inOrder(emptyObserver, emptyObserver)

        viewModel.commons().observeForever { }

        order.verify(emptyObserver).onChanged(false)
        order.verify(emptyObserver).onChanged(true)
    }

    @Test
    fun `not empty state`() {
        whenever(dataService.commons()).doAnswer { flowOf(listOf(CommonSection(), CommonSection())) }

        val emptyObserver = mock<Observer<Boolean>>()
        viewModel.empty.observeForever(emptyObserver)

        viewModel.commons().observeForever { }

        verify(emptyObserver, times(2)).onChanged(false)
    }

    @Test
    fun `successful fetch`() {

        val commonSection1 = CommonSection()
        val commonSection2 = CommonSection()

        whenever(dataService.commons()).doAnswer { flowOf(listOf(commonSection1, commonSection2)) }

        val sectionsObserver = mock<Observer<List<CommonSection>>>()

        viewModel.commons().observeForever(sectionsObserver)

        verify(sectionsObserver).onChanged(listOf(commonSection1, commonSection2))
    }


    @Test
    fun `failed fetch`() {

        whenever(dataService.commons()).doAnswer { flow { throw RuntimeException() } }

        val sectionsObserver = mock<Observer<List<CommonSection>>>()

        viewModel.commons().observeForever(sectionsObserver)

        verify(sectionsObserver).onChanged(listOf())
    }
}