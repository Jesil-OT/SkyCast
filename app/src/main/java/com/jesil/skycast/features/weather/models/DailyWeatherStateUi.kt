package com.jesil.skycast.features.weather.models

import java.time.Instant

data class DailyWeatherStateUi(
    val day: Instant,
    val weatherTypeIcon: String,
    val temperature: String,
    val minTemperature: String
)