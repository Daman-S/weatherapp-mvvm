package com.example.weatherapp.apiservice

import com.example.weatherapp.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("onecall?units=metric")
    suspend fun getWeather(@Query("lat") lat:Double, @Query("lon") lon:Double, @Query("appid") appid:String): Response<Weather>
}