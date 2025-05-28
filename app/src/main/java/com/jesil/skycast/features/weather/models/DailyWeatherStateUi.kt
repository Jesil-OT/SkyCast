package com.jesil.skycast.features.weather.models

data class DailyWeatherStateUi(
    val day: String,
    val weatherTypeIcon: String,
    val temperature: String,
    val minTemperature: String
)