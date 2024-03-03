package com.example.weatherapp.model

data class Weather(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val current: Current,
)