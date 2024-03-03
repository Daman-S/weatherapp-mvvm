package com.example.weatherapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.repository.FakeWeatherRepository
import com.example.weatherapp.rules.MainDispatcherRule
import com.example.weatherapp.util.WeatherUtility
import com.example.weatherapp.viewmodel.WeatherViewModel
import org.junit.Rule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

internal class WeatherViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: WeatherViewModel
    private lateinit var coordinateData: HashMap<String, WeatherUtility.Coordinates>

    @Before
    fun setUp() {
        viewModel = WeatherViewModel(FakeWeatherRepository())
        coordinateData = HashMap<String, WeatherUtility.Coordinates>()
        coordinateData.put("Baku", WeatherUtility.Coordinates(40.3755885, 49.8328009))
    }

    @Test
    fun `APIState is in success state if response is received successfully`() {
        viewModel.getWeather(coordinateData,"KEY")

        assertEquals("com.example.weatherapp.states.APIState\$Success", viewModel.weatherState.value.javaClass.name)

        assertEquals("Baku",viewModel.weatherState.value.current.location)

    }

    @Test
    fun `APIState is in loading state if response is empty`() {
        coordinateData.remove("Baku")

        viewModel.getWeather(coordinateData,"KEY")

        assertEquals("com.example.weatherapp.states.APIState\$Loading", viewModel.weatherState.value.javaClass.name)
    }

}