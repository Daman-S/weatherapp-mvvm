package com.example.weatherapp.repository


import com.example.weatherapp.apiservice.APIServiceImpl
import com.example.weatherapp.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl
@Inject
constructor(private val apiServiceImpl: APIServiceImpl) : WeatherRepository {
   override fun getWeather(lat: Double, lon:Double, appid:String): Flow<Weather> = flow {
      emit(apiServiceImpl.getWeather(lat, lon, appid).body())
   }.flowOn(Dispatchers.IO) as Flow<Weather>

}