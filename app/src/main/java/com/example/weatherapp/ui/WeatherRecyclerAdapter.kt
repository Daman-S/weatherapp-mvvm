package com.example.weatherapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.WeatherRowBinding
import com.example.weatherapp.util.WeatherUtility
import com.example.weatherapp.util.WeatherUtility.WeatherByLocation

class WeatherRecyclerAdapter(private var weatherList: List<WeatherByLocation>): RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder>() {
    private lateinit var binding: WeatherRowBinding

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        binding = WeatherRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return WeatherViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        binding.location.text = weatherList[position].location
        binding.temperature.text = weatherList[position].weather.current.temp.toString() + "°C"
        binding.feelslikevalue.text = weatherList[position].weather.current.feels_like.toString() + "°C"
        binding.humidityvalue.text = weatherList[position].weather.current.humidity.toString()

    }

    fun setData(weatherList: List<WeatherByLocation>)
    {
        if(weatherList.size == WeatherUtility.DATA_SIZE)
            this.weatherList = weatherList.sortedBy { it.location }

        notifyDataSetChanged()
    }
}