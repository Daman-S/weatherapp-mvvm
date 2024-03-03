package com.example.weatherapp.states

import com.example.weatherapp.model.Weather
import com.example.weatherapp.util.WeatherUtility

sealed class APIState{

    lateinit var current : WeatherUtility.WeatherByLocation

    object Loading : APIState()
    class Failure(val msg:Throwable) : APIState()
    class Success(val location:String, val data: Weather) : APIState()
    object Initial : APIState()
}