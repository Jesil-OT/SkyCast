package com.jesil.skycast.features.weather.models


data class WeatherStateUi(
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
    val hourlyWeather: List<HoursWeatherStateUi> = emptyList(),
    val dailyWeather: List<DailyWeatherStateUi> = emptyList()
)

sealed class WeatherViewState{
    object Idle: WeatherViewState()
    object Loading: WeatherViewState()
    data class Success(val data: WeatherStateUi): WeatherViewState()
    data class Error(val message: String): WeatherViewState()
}


