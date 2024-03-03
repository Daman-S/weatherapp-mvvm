package com.example.weatherapp.apiservice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.apiservice.APIService
import com.example.weatherapp.model.Current
import com.example.weatherapp.rules.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class APIServiceTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var APIService: APIService

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        APIService = Retrofit.Builder()
            .baseUrl(String.format("http://localhost:%s",mockWebServer.port))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.example.weatherapp.apiservice.APIService::class.java)
    }

    @Test
    fun testGetWeatherEmptyBody() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = APIService.getWeather(Mockito.anyDouble(),Mockito.anyDouble(),Mockito.anyString())
        mockWebServer.takeRequest()

        assertEquals(0.0, response.body()!!.lat,0.0)
        assertEquals(0.0, response.body()!!.lon,0.0)
        assertEquals(null, response.body()!!.timezone)
        assertEquals(null, response.body()!!.current)
    }

    @Test
    fun testGetWeatherSuccess() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{\n" +
                "    \"lat\": 40.3756,\n" +
                "    \"lon\": 49.8328,\n" +
                "    \"timezone\": \"Asia/Baku\",\n" +
                "    \"current\": {\n" +
                "        \"temp\": 24.01,\n" +
                "        \"feels_like\": 24.24,\n" +
                "        \"humidity\": 68\n" +
                "\t}\n" +
                "}\n")
        mockWebServer.enqueue(mockResponse)

        val response = APIService.getWeather(Mockito.anyDouble(),Mockito.anyDouble(),Mockito.anyString())
        mockWebServer.takeRequest()

        assertEquals(0.0, response.body()!!.lat,40.3756)
        assertEquals(0.0, response.body()!!.lon,49.8328)
        assertEquals("Asia/Baku", response.body()!!.timezone)
        assertEquals(Current(24.01,24.24,68), response.body()!!.current)
    }

    @Test
    fun testGetWeather404Failure() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Not found")
        mockWebServer.enqueue(mockResponse)

        val response = APIService.getWeather(Mockito.anyDouble(),Mockito.anyDouble(),Mockito.anyString())
        mockWebServer.takeRequest()

        assertFalse(response.isSuccessful)
    }

    @Test
    fun testGetWeather500Failure() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(500)
        mockResponse.setBody("Internal server error. Something went wrong")
        mockWebServer.enqueue(mockResponse)

        val response = APIService.getWeather(Mockito.anyDouble(),Mockito.anyDouble(),Mockito.anyString())
        mockWebServer.takeRequest()

        assertFalse(response.isSuccessful)
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}