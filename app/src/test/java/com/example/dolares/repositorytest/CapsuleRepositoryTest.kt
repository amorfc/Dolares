package com.example.dolares.repositorytest

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dolares.data.local.dao.CapsulesDao
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.remote.SpacexApiService
import com.example.dolares.data.repository.CapsulesRepository
import com.example.dolares.data.repository.LaunchesRepository
import com.example.dolares.dummy_test_data.createTestCapsuleDUMMY1
import com.example.dolares.dummy_test_data.createTestCapsuleDUMMY2
import com.example.dolares.dummy_test_data.createTestLaunchDUMMY1
import com.example.dolares.dummy_test_data.createTestLaunchDUMMY2
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class CapsuleRepositoryTest {


    private lateinit var capsulesRepository: CapsulesRepository
    @Mock
    private lateinit var capsulesDao: CapsulesDao
    @Mock
    private lateinit var sharedPrefs: SharedPreferences
    @Mock
    private lateinit var spaceService: SpacexApiService


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    //List of Launches
    private val listOfCapsules = listOf(createTestCapsuleDUMMY1(), createTestCapsuleDUMMY2())

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        capsulesRepository = CapsulesRepository(spaceService, capsulesDao, sharedPrefs)
    }
    @Test
    fun refreshDataTest_Api_Response_Error_Test() = runBlocking {
        val responseError: Response<List<Capsule>> = Response.error(
            403,
            ResponseBody.create(
                MediaType.parse("application/json"),
                "Bad Request"
            )
        )

        Mockito.`when`(spaceService.getAllCapsules()).thenAnswer {
            return@thenAnswer responseError
        }

        capsulesRepository.refreshData( true)

        verify(spaceService, times(1)).getAllCapsules()
        verify(capsulesDao, times(0)).replaceCapsulesData(listOfCapsules)
    }

    @Test
    fun getAllCapsulesFlowTest() = runBlocking {
        val capsulesFlow = flowOf(listOfCapsules)

        Mockito.`when`(capsulesDao.getAllCapsulesFlow()).thenAnswer {
            return@thenAnswer capsulesFlow
        }

        var result = listOf<Capsule>()
        capsulesRepository.getAllCapsulesFlow().collect{
            result = it
        }

        verify(capsulesDao, times(1)).getAllCapsulesFlow()
        Assert.assertEquals(result, listOfCapsules)
    }

    @Test
    fun refreshDataTest(): Unit = runBlocking {
        val responseSuccess: Response<List<Capsule>> = Response.success(listOfCapsules)

        Mockito.`when`(spaceService.getAllCapsules()).thenAnswer {
            return@thenAnswer responseSuccess
        }

        capsulesRepository.refreshData(true)

        verify(spaceService, times(1)).getAllCapsules()
    }
}