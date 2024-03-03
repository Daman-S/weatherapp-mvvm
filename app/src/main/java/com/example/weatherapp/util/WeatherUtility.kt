package com.example.weatherapp.util

import com.example.weatherapp.model.Weather

class WeatherUtility {

    companion object {
        val DATA_SIZE = 9
    }
    fun coordinateData(): HashMap<String, Coordinates> {
        var coordMap : HashMap<String, Coordinates> = HashMap<String, Coordinates> ()

        coordMap.put("Charlotte", Coordinates(40.7127281, -74.0060152))
        coordMap.put("Istanbul", Coordinates(41.0091982, 28.9662187))
        coordMap.put("Boston", Coordinates(42.3554334, -71.060511))
        coordMap.put("Baku", Coordinates(40.3755885, 49.8328009))
        coordMap.put("Mumbai", Coordinates(19.0785451, 72.878176))
        coordMap.put("Tokyo", Coordinates(35.6828387, 139.7594549))
        coordMap.put("Vladivostok", Coordinates(43.1150678, 131.8855768))
        coordMap.put("London", Coordinates(51.5073219, -0.1276474))
        coordMap.put("Paris", Coordinates(48.8588897, 2.3200410217200766))

        return coordMap;
    }
    class Coordinates (
        val lat: Double,
        val lon: Double
    )

    class WeatherByLocation (
        val location: String,
        val weather: Weather
    )
}