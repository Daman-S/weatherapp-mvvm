package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.states.APIState
import com.example.weatherapp.util.WeatherUtility
import com.example.weatherapp.util.WeatherUtility.Coordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val TAG = WeatherViewModel::class.java.simpleName
    private val state: MutableStateFlow<APIState> = MutableStateFlow(APIState.Initial)
    val weatherState: MutableStateFlow<APIState> = state

     fun getWeather(coordinateData: HashMap<String, Coordinates>, API_KEY: String) = viewModelScope.launch {
        state.value = APIState.Loading

        coordinateData.forEach { (key) ->
            repository.getWeather(coordinateData.get(key)!!.lat, coordinateData.get(key)!!.lon, API_KEY)
                .catch { error ->
                    Log.e(TAG, error.message.toString())
                    state.value = APIState.Failure(error)
                }.collect() { response ->
                    state.value = APIState.Success(key,response)
                    state.value.current = WeatherUtility.WeatherByLocation(key,response)
                }
        }

    }

}