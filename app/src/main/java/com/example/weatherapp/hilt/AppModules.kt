package com.example.weatherapp.hilt

import com.example.weatherapp.apiservice.APIService
import com.example.weatherapp.apiservice.APIServiceImpl
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    fun provideBaseUrl() = "http://api.openweathermap.org/data/3.0/"

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String): APIService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

    @Singleton
    @Provides
    fun provideWeatherRepository(
        apiServiceImpl: APIServiceImpl
    ) = WeatherRepositoryImpl(apiServiceImpl) as WeatherRepository


}