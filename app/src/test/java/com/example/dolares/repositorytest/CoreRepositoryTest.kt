package com.example.dolares.repositorytest

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dolares.data.local.dao.CoresDao
import com.example.dolares.data.local.dao.LaunchesDao
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.local.model.Core
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.remote.SpacexApiService
import com.example.dolares.data.repository.CoresRepository
import com.example.dolares.data.repository.LaunchesRepository
import com.example.dolares.dummy_test_data.createTestCoreDUMMY1
import com.example.dolares.dummy_test_data.createTestCoreDUMMY2
import com.example.dolares.dummy_test_data.createTestLaunchDUMMY1
import com.example.dolares.dummy_test_data.createTestLaunchDUMMY2
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.collect
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

class CoreRepositoryTest {


    private lateinit var coreRepository: CoresRepository
    @Mock
    private lateinit var coresDao: CoresDao
    @Mock
    private lateinit var sharedPrefs: SharedPreferences
    @Mock
    private lateinit var spaceService: SpacexApiService


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    //List of Launches
    private val listOfCores = listOf(createTestCoreDUMMY1(), createTestCoreDUMMY2())

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        coreRepository = CoresRepository(spaceService, coresDao, sharedPrefs)
    }

    @Test
    fun refreshDataTest_Api_Response_Error_Test() = runBlocking {
        val responseError: Response<List<Core>> = Response.error(
            403,
            ResponseBody.create(
                MediaType.parse("application/json"),
                "Bad Request"
            )
        )

        Mockito.`when`(spaceService.getAllCores()).thenAnswer {
            return@thenAnswer responseError
        }

        coreRepository.refreshData( true)

        verify(spaceService, times(1)).getAllCores()
        verify(coresDao, times(0)).replaceAllCores(listOfCores)
    }

    @Test
    fun getAllCoresFlowTest() = runBlocking {
        val coresFlow = flowOf(listOfCores)

        Mockito.`when`(coresDao.getAllCoresFlow()).thenAnswer {
            return@thenAnswer coresFlow
        }

        var result = listOf<Core>()
        coreRepository.getAllCoresFlowFromDb().collect{
            result = it
        }

        verify(coresDao, times(1)).getAllCoresFlow()
        Assert.assertEquals(result, listOfCores)
    }

    @Test
    fun refreshDataTest(): Unit = runBlocking {
        val responseSuccess: Response<List<Core>> = Response.success(listOfCores)

        Mockito.`when`(spaceService.getAllCores()).thenAnswer {
            return@thenAnswer responseSuccess
        }

        coreRepository.refreshData(true)

        verify(spaceService, times(1)).getAllCores()
    }
}