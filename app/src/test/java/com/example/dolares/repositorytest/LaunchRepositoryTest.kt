package com.example.dolares.repositorytest

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dolares.data.local.dao.LaunchesDao
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.remote.SpacexApiService
import com.example.dolares.data.repository.LaunchesRepository
import com.example.dolares.dummy_test_data.createTestLaunchDUMMY1
import com.example.dolares.dummy_test_data.createTestLaunchDUMMY2
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.flow.collect
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class LaunchRepositoryTest {


    private lateinit var launchesRepository: LaunchesRepository
    @Mock
    private lateinit var launchesDao: LaunchesDao
    @Mock
    private lateinit var sharedPrefs: SharedPreferences
    @Mock
    private lateinit var spaceService: SpacexApiService


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    //List of Launches
    private val listOfLaunches = listOf(createTestLaunchDUMMY1(), createTestLaunchDUMMY2())

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        launchesRepository = LaunchesRepository(spaceService, launchesDao, sharedPrefs)
    }

    @Test
    fun getAllLaunchesFlowTest() = runBlocking {
        val launchesFlow = flowOf(this@LaunchRepositoryTest.listOfLaunches)

        Mockito.`when`(launchesDao.getAllLaunchesFlow()).thenAnswer {
            return@thenAnswer launchesFlow
        }

        var result = listOf<Launch>()
        launchesRepository.getAllLaunchesFlowFromDb().collect{
            result = it
        }

        verify(launchesDao, times(1)).getAllLaunchesFlow()
        assertEquals(result, listOfLaunches)
    }

    @Test
    fun refreshDataTest_Api_Response_Error_Test() = runBlocking {
        val responseError: Response<List<Launch>> = Response.error(
            403,
            ResponseBody.create(
                MediaType.parse("application/json"),
                "Bad Request"
            )
        )

        Mockito.`when`(spaceService.getAllLaunches()).thenAnswer {
            return@thenAnswer responseError
        }

        launchesRepository.refreshData( true)

        verify(spaceService, times(1)).getAllLaunches()
        verify(launchesDao, times(0)).replaceAllLaunches(listOfLaunches)
    }

    @Test
    fun refreshDataTest(): Unit = runBlocking {
        val responseSuccess: Response<List<Launch>> = Response.success(listOfLaunches)

        Mockito.`when`(spaceService.getAllLaunches()).thenAnswer {
            return@thenAnswer responseSuccess
        }

        launchesRepository.refreshData(true)

        verify(spaceService, times(1)).getAllLaunches()
    }

}