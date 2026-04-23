package com.jesil.skycast.features.weather.models

import java.time.Instant

data class HoursWeatherStateUi(
    val time: Instant,
    val weatherTypeIcon: String,
    val temperature: String,
    val minTemperature: String
)
