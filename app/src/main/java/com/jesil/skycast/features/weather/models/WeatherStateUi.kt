package com.jesil.skycast.features.weather.models

import kotlin.math.roundToInt

data class WeatherStateUi(
    val weatherData: WeatherDataUi? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class WeatherDataUi(
    val location: String = "",
    val time: String = "",
    val temperature: String = "0.0°C",
    val weatherType: String = "",
    val weatherTypeDescription: String = "",
    val weatherTypeIcon: String = "",
    val windSpeed: String = "",
    val humidity: String = "",
    val rainChance: String = "",
    val timeZone: String = "",
    val sunrise: String = "",
    val sunset: String = "",
    val pressure: String = "",
    val minTemperature: String = "",
    val hourlyWeather: List<HoursWeatherStateUi> = emptyList()
)


