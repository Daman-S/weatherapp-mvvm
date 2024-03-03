package com.example.weatherapp.repository

import com.example.weatherapp.model.Current
import com.example.weatherapp.model.Weather
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow



class FakeWeatherRepository: WeatherRepository {

    override fun getWeather(lat: Double, lon: Double, appid: String): Flow<Weather> {
        return flow { emit(Weather(49.8328009,49.8328009,"Asia/Baku", Current(25.01,25.39, 70))) }
    }
}