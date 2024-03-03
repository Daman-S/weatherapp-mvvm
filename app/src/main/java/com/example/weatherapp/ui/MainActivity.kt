package com.example.weatherapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.BuildConfig.WEATHER_API_KEY
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.states.APIState
import com.example.weatherapp.util.WeatherUtility
import com.example.weatherapp.util.WeatherUtility.WeatherByLocation
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = WeatherViewModel::class.java.simpleName

    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherAdapter: WeatherRecyclerAdapter
    private val weatherViewModel:WeatherViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerview()

        var weatherList = ArrayList<WeatherByLocation>()

        Log.d(TAG, "Making API calls")
        weatherViewModel.getWeather(WeatherUtility().coordinateData(), WEATHER_API_KEY)

        lifecycleScope.launchWhenStarted {
            weatherViewModel.weatherState.collect {res ->
                when(res){
                    is APIState.Loading -> {
                        binding.recyclerview.isVisible=false
                        binding.progressBar.isVisible=true
                    }
                    is APIState.Failure -> {
                        binding.recyclerview.isVisible = false
                        binding.progressBar.isVisible = false
                        Log.e("main", "API error: ${res.msg}")
                    }
                    is APIState.Success -> {
                        binding.recyclerview.isVisible = true
                        binding.progressBar.isVisible = false
                        weatherList.add(WeatherByLocation(res.location,res.data))
                        weatherAdapter.setData(weatherList)
                        weatherAdapter.notifyDataSetChanged()
                        Log.d(TAG, "API success: ${res.data}")
                    }
                    is APIState.Initial -> {

                    }
                }
            }
        }

    }

    private fun initRecyclerview() {
        weatherAdapter = WeatherRecyclerAdapter(ArrayList())
        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter=weatherAdapter
        }
    }
}