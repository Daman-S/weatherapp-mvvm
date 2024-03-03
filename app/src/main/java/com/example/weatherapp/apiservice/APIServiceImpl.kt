package com.example.weatherapp.apiservice

import com.example.weatherapp.model.Weather
import retrofit2.Response
import javax.inject.Inject

class APIServiceImpl @Inject constructor(private val apiService: APIService) {

    suspend fun getWeather(lat: Double, lon:Double, appid:String):Response<Weather> = apiService.getWeather(lat, lon, appid)
}
