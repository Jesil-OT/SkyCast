package com.jesil.skycast.data.model

import java.time.Instant

data class HoursWeather(
    val time: Instant = Instant.now(),
    val weatherTypeIcon: String = "",
    val temperature: Int = 0,
    val minTemperature: Int = 0
)
