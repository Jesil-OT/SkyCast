package com.jesil.skycast.data.model

import java.time.Instant

data class CurrentDailyWeather(
    val id: Int = 0,
    val location: String = "",
    val country: String = "",
    val temperature: Int = 0,
    val weatherType: String = "",
    val weatherTypeDescription: String = "",
    val weatherTypeIcon: String,
    val windSpeed: Int = 0,
    val humidity: Int = 0,
    val timeZone: Instant = Instant.now(),
    val sunrise: Instant = Instant.now(),
    val sunset: Instant = Instant.now(),
    val pressure: Int = 0,
    val visibility: Int = 0,
    val seaLevel : Int = 0,
    val minTemperature: Int = 0,
    val hourlyWeather: List<CurrentDailyWeather> = emptyList(),
    val dailyWeather: List<CurrentDailyWeather> = emptyList()
)



