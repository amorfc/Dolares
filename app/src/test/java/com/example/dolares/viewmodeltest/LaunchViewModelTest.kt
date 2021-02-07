package com.example.dolares.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.repository.LaunchesRepository
import com.example.dolares.dummy_test_data.createTestLaunchDUMMY1
import com.example.dolares.dummy_test_data.createTestLaunchDUMMY2
import com.example.dolares.ui.launches.LaunchesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations



class LaunchViewModelTest {

    private val testLaunchesList = listOf(createTestLaunchDUMMY1(), createTestLaunchDUMMY2())

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var launchesRepository: LaunchesRepository

    // class under test
    private lateinit var launchesViewModel: LaunchesViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}