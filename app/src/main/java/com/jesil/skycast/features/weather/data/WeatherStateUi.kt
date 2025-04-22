package com.jesil.skycast.features.weather.data

data class WeatherStateUi(
    val weatherData: WeatherDataUi? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class WeatherDataUi(
    val location: String,
    val time: String,
    val temperature: String,
    val weatherType: String,
    val icon: Int,
    val windSpeed: String,
    val humidity: String,
    val rainChance: String,
    val timeZone: String
)
