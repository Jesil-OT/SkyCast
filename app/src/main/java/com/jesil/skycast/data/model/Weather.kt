package com.jesil.skycast.data.model

import java.time.Instant

data class Weather(
    val id: Int = 0,
    val location: String = "",
    val temperature: Double = 0.0,
    val weatherType: String = "",
    val weatherTypeDescription: String = "",
    val weatherTypeIcon: String,
    val windSpeed: Double = 0.0,
    val humidity: Int = 0,
    val timeZone: Instant = Instant.now(),
    val sunrise: Instant = Instant.now(),
    val sunset: Instant = Instant.now(),
    val pressure: Int = 0,
    val minTemperature: Double = 0.0,
    val hourlyWeather: List<HoursWeather> = emptyList()
)

data class HoursWeather(
    val time: Instant = Instant.now(),
    val weatherTypeIcon: String = "",
    val temperature: Double = 0.0,
    val minTemperature: Double = 0.0
)
