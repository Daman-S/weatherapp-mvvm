package com.example.weatherapp.repository

import com.example.weatherapp.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(lat: Double, lon:Double, appid:String): Flow<Weather>
}